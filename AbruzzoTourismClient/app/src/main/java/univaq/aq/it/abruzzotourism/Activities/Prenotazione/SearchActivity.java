package univaq.aq.it.abruzzotourism.Activities.Prenotazione;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.Activities.ProfiloTurista.ProfiloActivity;
import univaq.aq.it.abruzzotourism.Adapter.AttivitaListAdapter;
import univaq.aq.it.abruzzotourism.MainActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.utility.RESTClient;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class SearchActivity extends AppCompatActivity {

    private AttivitaListAdapter list_adapter;
    private ListView lv_attivita;
    private UserDetails user = new UserDetails();
    private Activity searchActivity = this;
    Context context = this;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        userLocalStore = new UserLocalStore(context);
        this.user = userLocalStore.getLoggedInUser();
        fetchAttivita(getIntent().getStringExtra("tipologia"));

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

    public void fetchAttivita(final String tipologia){
        RESTClient.get("/Attivita/" + tipologia, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    List<Attivita> attivita = new ArrayList<Attivita>();
                    String response = new String(responseBody);
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Attivita att = new Attivita();
                        att.setNomeAttivita(jsonArray.getJSONObject(i).getString("nomeAttivita"));
                        att.setDescrizione(jsonArray.getJSONObject(i).getString("descrizione"));
                        att.setIDAttivita(jsonArray.getJSONObject(i).getInt("idattività"));
                        att.setCostoPerPersona(Float.parseFloat(jsonArray.getJSONObject(i).getString("costoPerPersona")));
                        att.setNumMaxPartecipanti(jsonArray.getJSONObject(i).getInt("numMaxPartecipanti"));
                        att.setImage(jsonArray.getJSONObject(i).getString("image"));
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


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(searchActivity, "ERRORE!!! Riprovare", Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
