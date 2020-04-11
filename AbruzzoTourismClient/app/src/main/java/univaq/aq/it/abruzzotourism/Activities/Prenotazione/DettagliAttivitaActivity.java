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

import univaq.aq.it.abruzzotourism.Activities.ProfiloTurista.ProfiloActivity;
import univaq.aq.it.abruzzotourism.Activities.Search.SearchActivity;
import univaq.aq.it.abruzzotourism.Activities.Home.MainActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.utility.SOAPClient;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class DettagliAttivitaActivity extends AppCompatActivity {

    Context context = this;
    UserDetails user = new UserDetails();
    UserLocalStore userLocalStore;
    Attivita attivita = new Attivita();
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettagli_attivita);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        userLocalStore = new UserLocalStore(context);
        user = userLocalStore.getLoggedInUser();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBarDettaggliAttivita);
        progressBar.setIndeterminate(true);

        attivita = getIntent().getParcelableExtra("attivita");

        getImage task = new getImage();
        task.execute(attivita.getIDAttivita(), user);


        TextView tv = findViewById(R.id.tv_nomeAttivitaDettaglio);
        tv.setText(attivita.getNomeAttivita());
        TextView tv2 = findViewById(R.id.tv_descrizioneAttivitaDettaglio);
        tv2.setText(attivita.getDescrizione());
        TextView tv3 = findViewById(R.id.costoPersona);
        tv3.setText("Il costo per persona è : "+attivita.getCostoPerPersona()+"€");

        Button btn = findViewById(R.id.btn_prenota);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PrenotaActivity.class);
                attivita.setImage("");
                i.putExtra("attivita", attivita);
                context.startActivity(i);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class getImage extends AsyncTask<Object, Void, SoapObject> {
        private String TAG = "SoapClient";
        private SOAPClient soapClient = new SOAPClient();


        @Override
        protected SoapObject doInBackground(Object... params){
            return soapClient.getImageAttivita(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(SoapObject result){
            progressBar.setIndeterminate(false);
            attivita.setImage(String.valueOf(result.getProperty(0)));
            ImageView iv= findViewById(R.id.im_DettagliAttivita);
            byte[] image = Base64.decode(attivita.getImage(), 0);
            Bitmap image1 = BitmapFactory.decodeByteArray(image,0,image.length);
            iv.setImageBitmap(image1);

        }

        @Override
        protected void onPreExecute(){
            //progressBar = findViewById(R.id.progressBarRiepilogoPrenotazione);
            progressBar.setIndeterminate(true);
        }


    }



}

