package univaq.aq.it.abruzzotourism.Activities.Prenotazione;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;

import univaq.aq.it.abruzzotourism.Activities.ProfiloTurista.ProfiloActivity;
import univaq.aq.it.abruzzotourism.Activities.Search.SearchActivity;
import univaq.aq.it.abruzzotourism.Activities.Home.MainActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.Prenotazione;
import univaq.aq.it.abruzzotourism.domain.Turista;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.utility.SOAPClient;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class RiepilogoPrenotazioneActivity extends AppCompatActivity {

    Context context = this;
    UserLocalStore userLocalStore;
    ProgressBar progressBar;
    UserDetails user = new UserDetails();
    Attivita att = new Attivita();
    Turista turistaPrenotante = new Turista();
    Prenotazione prenotazione = new Prenotazione();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riepilogo_prenotazione);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        userLocalStore = new UserLocalStore(context);

        this.user = userLocalStore.getLoggedInUser();
        this.att = getIntent().getParcelableExtra("attivita");

        ImageByIDAttivita task = new ImageByIDAttivita();
        task.execute(att.getIDAttivita(), user);

        final int numPersone = (int) (getIntent().getFloatExtra("costoTotale",0)/att.getCostoPerPersona());


        TextView tv_nome = findViewById(R.id.tv_nome_attivita_riepilogo);
        TextView tv_persone = findViewById(R.id.tv_numeroPersone_riepilogo);
        TextView tv_data = findViewById(R.id.tv_data_riepilogo);
        TextView tv_costo = findViewById(R.id.tv_costoTot_riepilogo);
        tv_nome.setText(this.att.getNomeAttivita());
        tv_persone.setText("Persone per cui si intende prenotare : "+numPersone);
        tv_data.setText("data prevista per lo svolgimento dell'attività : "+ getIntent().getStringExtra("data"));
        tv_costo.setText("costo Totale della prenotazione : "+ getIntent().getFloatExtra("costoTotale", 0)+"€");


        Button btn_conferma = findViewById(R.id.btn_confermaPrenotazione);
        btn_conferma.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TuristaByEmail task = new TuristaByEmail();
                task.execute(user);
            }
        });



        Button btn_annulla3 = findViewById(R.id.btn_annulla3);
        btn_annulla3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PrenotaActivity.class);
                finish();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            final String[] tipologia = {"Sportiva", "Culturale", "floraEFauna"};
            AlertDialog.Builder window;
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    window = new AlertDialog.Builder(context);
                    window.setTitle("Scegli una categoria di ricerca");
                    window.setItems(tipologia, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which == 0){
                                Intent i = new Intent(context, SearchActivity.class);
                                i.putExtra("tipologia", "Sportiva");
                                context.startActivity(i);
                            }else if(which == 1){
                                Intent i = new Intent(context, SearchActivity.class);
                                i.putExtra("tipologia", "Culturale");
                                context.startActivity(i);
                            }else if(which == 2){
                                Intent i = new Intent(context, SearchActivity.class);
                                i.putExtra("tipologia", "FloraEFauna");
                                context.startActivity(i);
                            }else{
                                //theres an error in what was selected
                                Toast.makeText(getApplicationContext(), "Hmmm I messed up. I detected that you clicked on : " + which + "?", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    window.show();
                    return true;
                case R.id.navigation_home:
                    Intent i = new Intent(context, MainActivity.class);
                    context.startActivity(i);
                    return true;
                case R.id.navigation_profilo:
                    Intent in = new Intent(context, ProfiloActivity.class);
                    context.startActivity(in);
                    return true;

            }
            return false;
        }
    };

    public class ImageByIDAttivita extends AsyncTask<Object, Void, SoapObject>{
        private String TAG = "SoapClient";
        private SOAPClient soapClient = new SOAPClient();


        @Override
        protected SoapObject doInBackground(Object... params){
            return soapClient.getImageAttivita(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(SoapObject result){
            progressBar.setIndeterminate(false);
            att.setImage(result.getPropertyAsString(0));
            ImageView iv= findViewById(R.id.imageViewRiepilogo);
            byte[] image = Base64.decode(att.getImage(), 0);
            Bitmap image1 = BitmapFactory.decodeByteArray(image,0,image.length);
            iv.setImageBitmap(image1);

        }

        @Override
        protected void onPreExecute(){
            progressBar = findViewById(R.id.progressBarRiepilogoPrenotazione);
            progressBar.setIndeterminate(true);
        }
    }

    public class TuristaByEmail extends AsyncTask<UserDetails, Void, SoapObject>{

        private String TAG = "SoapClient";
        private SOAPClient soapClient = new SOAPClient();

        @Override
        protected SoapObject doInBackground(UserDetails... params){
            return soapClient.getTuristaByEmail(params[0]);
        }

        @Override
        protected void onPostExecute(SoapObject result){
            SoapObject soapObject = (SoapObject) result.getProperty(0);
            turistaPrenotante.setNome(soapObject.getPropertyAsString("Nome"));
            turistaPrenotante.setDataNascita(soapObject.getPropertyAsString("dataNascita"));
            turistaPrenotante.setEmail(soapObject.getPropertyAsString("email"));
            turistaPrenotante.setIDTurista(Integer.parseInt(soapObject.getPropertyAsString("IDTurista")));
            turistaPrenotante.setPassword(soapObject.getPropertyAsString("password"));
            //turistaPrenotante.setImage(soapObject.getPropertyAsString("image"));

            prenotazione.setTuristaPrenotante(turistaPrenotante);
            prenotazione.setCosto(getIntent().getFloatExtra("costoTotale", 0));
            prenotazione.setPagata(true);
            prenotazione.setConfermata(true);
            prenotazione.setAttivita(att);
            prenotazione.setNumPartecipanti((int) (prenotazione.getCosto()/prenotazione.getAttivita().getCostoPerPersona()));
            Calendar calendar = Calendar.getInstance();
            prenotazione.setDataDiPrenotazione(calendar.getTime().toString());
            prenotazione.setDataSvolgimentoAttivita(getIntent().getStringExtra("data"));
            prenotazione.setPrenotazioneANomeDi(turistaPrenotante.getNome());

            ConfermaPrenotazioneWS task = new ConfermaPrenotazioneWS();
            task.execute(prenotazione, user);

        }

    }

    public class ConfermaPrenotazioneWS extends AsyncTask<Object, Void, SoapObject> {
        private String TAG = "SoapClient";
        private SOAPClient soapClient = new SOAPClient();


        @Override
        protected SoapObject doInBackground(Object... params){
            return soapClient.confermaPrenotazione(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(SoapObject result){
            progressBar.setIndeterminate(false);
            String prenotazione_avvenuta = "La tua prenotazione è stata effettuata con successo!!! Puoi visionare le prenotazioni effettuate nel registro prenotazioni.";
            AlertDialog alertDialog = new AlertDialog.Builder(RiepilogoPrenotazioneActivity.this).create();
            alertDialog.setTitle("PREANOTAZIONE AVVENUTA");
            alertDialog.setMessage(prenotazione_avvenuta);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent i = new Intent(context, MainActivity.class);
                            context.startActivity(i);
                            finish();
                        }
                    });
            alertDialog.show();

        }

        @Override
        protected void onPreExecute(){
            progressBar = findViewById(R.id.progressBarRiepilogoPrenotazione);
            progressBar.setIndeterminate(true);
        }


    }

}
