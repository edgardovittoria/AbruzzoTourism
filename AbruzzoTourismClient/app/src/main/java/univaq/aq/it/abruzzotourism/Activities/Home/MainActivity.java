package univaq.aq.it.abruzzotourism.Activities.Home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

import univaq.aq.it.abruzzotourism.Activities.Search.SearchActivity;
import univaq.aq.it.abruzzotourism.Activities.ProfiloTurista.ProfiloActivity;
import univaq.aq.it.abruzzotourism.Activities.Home.Adapter.AttivitaListAdapter;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.domain.UtenteAttivita;
import univaq.aq.it.abruzzotourism.Activities.login.Login;
import univaq.aq.it.abruzzotourism.utility.SOAPClient;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;


public class MainActivity extends AppCompatActivity {

    ListView lv_attivita;
    AttivitaListAdapter list_adapter;
    MainActivity mainActivity = this;
    static UserDetails user = new UserDetails();
    Context context = this;
    UserLocalStore userLocalStore;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        userLocalStore = new UserLocalStore(context);
        this.user = userLocalStore.getLoggedInUser();
        fillHomeWS task = new fillHomeWS();
        task.execute(this.user);

    }

    public static UserDetails getUser() {
        return user;
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


    public class fillHomeWS extends AsyncTask<UserDetails, Void, SoapObject> {
        private String TAG = "SoapClient";
        private SOAPClient soapClient = new SOAPClient();


        @Override
        protected SoapObject doInBackground(UserDetails... params){
            return soapClient.getAttivitaHome(params[0]);
        }


        @Override
        protected void onPostExecute(SoapObject result){
            progressBar.setIndeterminate(false);
            if(result == null){
                Toast.makeText(getApplicationContext(), "Email o Password ERRATI!!! ", Toast.LENGTH_LONG).show();
                userLocalStore.clearUserData();
                Intent i = new Intent(context, Login.class);
                context.startActivity(i);
            }
            else{
                List<SoapObject> soapObjects = new ArrayList<SoapObject>();
                List<Attivita> attivita = new ArrayList<Attivita>();
                for(int i = 0; i < result.getPropertyCount(); i++){

                    soapObjects.add((SoapObject) result.getProperty(i));

                    int ID = Integer.parseInt(soapObjects.get(i).getProperty("IDAttività").toString());
                    String nome = soapObjects.get(i).getProperty("nomeAttivita").toString();
                    String descrizione = soapObjects.get(i).getProperty("descrizione").toString();
                    float costo = Float.parseFloat(soapObjects.get(i).getProperty("CostoPerPersona").toString());
                    int numMaxPar = Integer.parseInt(soapObjects.get(i).getProperty("NumMaxPartecipanti").toString());
                    String image = soapObjects.get(i).getProperty("image").toString();
                    String tipologia = soapObjects.get(i).getProperty("tipologia").toString();

                    SoapObject soapObject2 = (SoapObject) soapObjects.get(i).getProperty("utenteAttivita");

                    int IDUtenteAttivita = Integer.parseInt(soapObject2.getProperty("IDUtenteAttivita").toString());
                    String nomeUtenteAttivita = soapObject2.getProperty("nomeUtenteAttivita").toString();
                    String email = soapObject2.getProperty("email").toString();
                    String password = soapObject2.getProperty("password").toString();

                    UtenteAttivita utenteAttivita = new UtenteAttivita(email, nomeUtenteAttivita, password);
                    utenteAttivita.setIDUtenteAttivita(IDUtenteAttivita);

                    Attivita att = new Attivita(ID, nome, descrizione, costo, numMaxPar, image, tipologia, utenteAttivita);
                    attivita.add(att);

                }

                list_adapter = new AttivitaListAdapter(mainActivity, attivita, user);
                lv_attivita = (ListView) findViewById(R.id.lv_attivita_search);
                lv_attivita.setAdapter(list_adapter);

            }


        }

        @Override
        protected void onPreExecute(){
            progressBar = findViewById(R.id.progressBar);
            progressBar.setIndeterminate(true);
        }

    }

}
