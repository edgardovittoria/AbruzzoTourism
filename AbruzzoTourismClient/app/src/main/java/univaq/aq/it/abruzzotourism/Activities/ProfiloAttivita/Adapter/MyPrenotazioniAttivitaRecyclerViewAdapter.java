package univaq.aq.it.abruzzotourism.Activities.ProfiloAttivita.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.Activities.ProfiloAttivita.ModificaPrenotazioneActivity;
import univaq.aq.it.abruzzotourism.Activities.ProfiloAttivita.ProfiloAttivitaActivity;
import univaq.aq.it.abruzzotourism.Activities.ProfiloTurista.RegistroPrenotazioniFragment.OnListFragmentInteractionListener;
import univaq.aq.it.abruzzotourism.PrenotazioneItem.PrenotazioneItem;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.utility.RESTClient;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PrenotazioneItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyPrenotazioniAttivitaRecyclerViewAdapter extends RecyclerView.Adapter<MyPrenotazioniAttivitaRecyclerViewAdapter.ViewHolder> {

    private final List<PrenotazioneItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public MyPrenotazioniAttivitaRecyclerViewAdapter(List<PrenotazioneItem> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_prenotazioni_attivita, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getContent());
        holder.mDetails.setText(mValues.get(position).getDetails());
        holder.mBtnModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ModificaPrenotazioneActivity.class);
                i.putExtra("IDPrenotazione", mValues.get(position).getIDPrenotazione());
                i.putExtra("dataSvolgimentoAttivita", mValues.get(position).getDataSvolgimentoAttivita());
                i.putExtra("numPartecipanti", mValues.get(position).getNumPartecipanti());
                i.putExtra("costo", mValues.get(position).getCosto());
                mContext.startActivity(i);
            }
        });
        holder.mBtnElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String elimina = "Sei Sicuro di voler eliminare la prenotazione?!!";
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle("ELIMINA");
                alertDialog.setMessage(elimina);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                RequestParams requestParams = new RequestParams();
                                requestParams.put("IDPrenotazione", mValues.get(position).getIDPrenotazione());
                                RESTClient.delete("/ProfiloAttivitaService/prenotazioni", requestParams, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        String successo = "La cancellazione è stata effettuata con successo!!!";
                                        Toast.makeText(mContext, successo, successo.length()).show();
                                        Intent i = new Intent(mContext, ProfiloAttivitaActivity.class);
                                        mContext.startActivity(i);
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        String errore = "La cancellazione NON è stata effettuata!!!RIPROVARE";
                                        Toast.makeText(mContext, errore, errore.length()).show();
                                    }
                                });
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ANNULLA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDetails;
        public final Button mBtnModifica;
        public final Button mBtnElimina;
        public PrenotazioneItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number_attivita);
            mContentView = (TextView) view.findViewById(R.id.content_attivita);
            mDetails = (TextView) view.findViewById(R.id.details_attivita);
            mBtnModifica = view.findViewById(R.id.btn_modifica_prenotazione);
            mBtnElimina = view.findViewById(R.id.btn_elemina_prenotazione);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
