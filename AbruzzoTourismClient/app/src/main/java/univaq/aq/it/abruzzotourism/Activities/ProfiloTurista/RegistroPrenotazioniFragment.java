package univaq.aq.it.abruzzotourism.Activities.ProfiloTurista;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.Adapter.MyPrenotazioniRecyclerViewAdapter;
import univaq.aq.it.abruzzotourism.MainActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.Prenotazione;
import univaq.aq.it.abruzzotourism.domain.Turista;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.PrenotazioneItem.PrenotazioneItem;
import univaq.aq.it.abruzzotourism.utility.RESTClient;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RegistroPrenotazioniFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private UserDetails user = MainActivity.getUser();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RegistroPrenotazioniFragment() {}



    @SuppressWarnings("unused")
    public static RegistroPrenotazioniFragment newInstance(int columnCount) {
        RegistroPrenotazioniFragment fragment = new RegistroPrenotazioniFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prenotazioni_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

                RESTClient.get("/TuristaByEmail/" + user.getEmail(), null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(responseBody));
                            Turista turista = new Turista();
                            turista.setIDTurista(jsonObject.getInt("idturista"));
                            turista.setEmail(jsonObject.getString("email"));
                            turista.setPassword(jsonObject.getString("password"));
                            turista.setNome(jsonObject.getString("nome"));
                            turista.setDataNascita(jsonObject.getString("dataNascita"));
                            RESTClient.get("/getPrenotazioniByIdTurista/" + turista.getIDTurista(), null, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    try {
                                        List<Prenotazione> prenotazioni = new ArrayList<>();
                                        List<PrenotazioneItem> prenotazioneItemList = new ArrayList<>();
                                        JSONArray result = new JSONArray(new String(responseBody));
                                        for(int i = 0; i<result.length();i++){
                                            Prenotazione prenotazione = new Prenotazione();
                                            Attivita att = new Attivita();
                                            JSONObject attivita = result.getJSONObject(i).getJSONObject("attivita");
                                            att.setNomeAttivita(attivita.getString("nomeAttivita"));
                                            prenotazione.setDataSvolgimentoAttivita(result.getJSONObject(i).getString("dataSvolgimentoAttivita"));
                                            prenotazione.setAttivita(att);
                                            prenotazione.setNumPartecipanti(result.getJSONObject(i).getInt("numPartecipanti"));
                                            prenotazione.setCosto(Float.parseFloat(result.getJSONObject(i).getString("costo")));
                                            prenotazioni.add(prenotazione);

                                            PrenotazioneItem prenotazioneItem = new PrenotazioneItem();
                                            prenotazioneItem.setId(String.valueOf(i+1));
                                            prenotazioneItem.setContent(prenotazioni.get(i).getAttivita().getNomeAttivita());
                                            prenotazioneItem.setDetails("data e ora : "+prenotazioni.get(i).getDataSvolgimentoAttivita()+". Prenotazione per : "+prenotazioni.get(i).getNumPartecipanti()+" Persone."+" Costo prenotazione: "+prenotazioni.get(i).getCosto()+"€");

                                            prenotazioneItemList.add(prenotazioneItem);
                                        }
                                        recyclerView.setAdapter(new MyPrenotazioniRecyclerViewAdapter(prenotazioneItemList, mListener));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        return view;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(PrenotazioneItem item);
    }
}
