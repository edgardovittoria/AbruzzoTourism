package univaq.aq.it.abruzzotourism.Activities.AggiungiAttivita.ProfiloAttivita;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.login.Login;
import univaq.aq.it.abruzzotourism.utility.RESTClient;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class ProfiloAttivitaActivity extends AppCompatActivity {

    Context context = this;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo_attivita);

        userLocalStore = new UserLocalStore(context);

        final UserDetails userDetails = userLocalStore.getLoggedInUser();


        RESTClient.get("/AttivitaByEmail/" + userDetails.getEmail(), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //recupero l'ggetto json ricevuto come risposta
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    //creo l'attività grazie all'oggetto json
                    final Attivita attivita = new Attivita();
                    attivita.setIDAttivita(jsonObject.getInt("idattività"));
                    attivita.setNomeAttivita(jsonObject.getString("nomeAttivita"));
                    attivita.setTipologia(jsonObject.getString("tipologia"));
                    attivita.setDescrizione(jsonObject.getString("descrizione"));
                    attivita.setNumMaxPartecipanti(jsonObject.getInt("numMaxPartecipanti"));
                    attivita.setCostoPerPersona(Float.parseFloat(jsonObject.get("costoPerPersona").toString()));
                    attivita.setImage(jsonObject.getString("image"));


                    RequestParams requestParams = new RequestParams();
                    requestParams.put("email", userDetails.getEmail());
                    requestParams.put("password", userDetails.getPassword());
                    requestParams.setUseJsonStreamer(true);
                    requestParams.setElapsedFieldInJsonStreamer(null);

                    RESTClient.post("/loginUtenteAttivita", requestParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String result = new String(responseBody);
                            if (result.equals("true")){

                                byte[] imagebyte = Base64.decode(attivita.getImage(), 0);
                                Bitmap imagebitmap = BitmapFactory.decodeByteArray(imagebyte,0,imagebyte.length);
                                ImageView image = findViewById(R.id.img_profilo_attivita);
                                image.setImageBitmap(imagebitmap);

                                TextView nome_attivita = findViewById(R.id.tv_nome_profilo_attivita);
                                nome_attivita.setText(attivita.getNomeAttivita());

                                FragmentManager fm = getSupportFragmentManager();
                                final Fragment fragment = fm.findFragmentById(R.id.fragment_profilo_attivita);
                                hideFrangment(fragment);

                                final View divider = findViewById(R.id.divider14);

                                FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton_profilo_attivita);
                                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(fragment.isHidden()){
                                            showFrangment(fragment);
                                            divider.setVisibility(View.VISIBLE);
                                        }else {
                                            hideFrangment(fragment);
                                            divider.setVisibility(View.INVISIBLE);

                                        }

                                    }
                                });
                            }else{

                                String errore = "Email o Password ERRATI!!!Riprovare.";
                                Toast.makeText(context, errore, errore.length()).show();
                                Intent i = new Intent(context, Login.class);
                                context.startActivity(i);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(context, error.getMessage(), error.getMessage().length()).show();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, error.getMessage(), error.getMessage().length()).show();
            }
        });

    }

    public void hideFrangment(Fragment fragment){
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // hide the Fragment
        fragmentTransaction.hide(fragment);
        // save the changes
        fragmentTransaction.commit();
    }

    public void showFrangment(Fragment fragment){
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // hide the Fragment
        fragmentTransaction.show(fragment);
        // save the changes
        fragmentTransaction.commit();
    }

}
