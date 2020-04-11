package univaq.aq.it.abruzzotourism.Activities.Search;

import android.app.Activity;
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

import univaq.aq.it.abruzzotourism.Activities.ProfiloTurista.ProfiloActivity;
import univaq.aq.it.abruzzotourism.Activities.Home.Adapter.AttivitaListAdapter;
import univaq.aq.it.abruzzotourism.Activities.Home.MainActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.utility.SOAPClient;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class SearchActivity extends AppCompatActivity {

    private AttivitaListAdapter list_adapter;
    private ListView lv_attivita;
    private UserDetails user = new UserDetails();
    private Activity searchActivity = this;
    ProgressBar progressBar;
    Context context = this;
    UserLocalStore userLocalStore;
    String tipologia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        progressBar = findViewById(R.id.progressBarSearch);
        progressBar.setIndeterminate(true);
        userLocalStore = new UserLocalStore(context);
        user = userLocalStore.getLoggedInUser();
        tipologia = getIntent().getStringExtra("tipologia");

        SearchAttivita task = new SearchAttivita();
        task.execute(tipologia, user);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class SearchAttivita extends AsyncTask<Object, Void, SoapObject>{

        private String TAG = "SoapClient";
        private SOAPClient soapClient = new SOAPClient();


        @Override
        protected SoapObject doInBackground(Object... params){
            return soapClient.getAttivitaByTipologia(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(SoapObject result){
            progressBar.setIndeterminate(false);
            List<Attivita> attivita = new ArrayList<Attivita>();
            for (int i = 0; i < result.getPropertyCount(); i++) {
                Attivita att = new Attivita();
                SoapObject soapObject = (SoapObject) result.getProperty(i);
                att.setNomeAttivita(soapObject.getPropertyAsString("nomeAttivita"));
                att.setDescrizione(String.valueOf(soapObject.getProperty("descrizione")));
                att.setIDAttivita(Integer.parseInt(soapObject.getPropertyAsString("IDAttività")));
                att.setCostoPerPersona(Float.parseFloat(soapObject.getPropertyAsString("CostoPerPersona")));
                att.setNumMaxPartecipanti(Integer.parseInt(soapObject.getPropertyAsString("NumMaxPartecipanti")));
                att.setImage(soapObject.getPropertyAsString("image"));
                attivita.add(att);
            }
            if(!attivita.isEmpty()){
                list_adapter = new AttivitaListAdapter(searchActivity, attivita, user);
                lv_attivita = (ListView) findViewById(R.id.lv_attivita_search);
                lv_attivita.setAdapter(list_adapter);
            }else {
                Toast.makeText(searchActivity, "Non sono state trovate attivita relative alla categoria : "+tipologia+"!!!", Toast.LENGTH_LONG).show();
                finish();
            }
        }

        @Override
        protected void onPreExecute(){
            progressBar.setIndeterminate(false);
        }
    }
}
