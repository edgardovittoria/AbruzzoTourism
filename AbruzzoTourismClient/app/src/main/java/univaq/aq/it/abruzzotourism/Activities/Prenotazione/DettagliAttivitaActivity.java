package univaq.aq.it.abruzzotourism.Activities.Prenotazione;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.MainActivity;
import univaq.aq.it.abruzzotourism.Activities.ProfiloTurista.ProfiloActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.utility.RESTClient;

public class DettagliAttivitaActivity extends AppCompatActivity {

    Context context = this;
    UserDetails user = new UserDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettagli_attivita);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        user = getIntent().getParcelableExtra("user");


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Attivita att = getIntent().getParcelableExtra("attivita");

        RESTClient.get("/getImage/" + att.getIDAttivita(), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                att.setImage(new String(responseBody));
                ImageView iv= findViewById(R.id.im_DettagliAttivita);
                byte[] image = Base64.decode(att.getImage(), 0);
                Bitmap image1 = BitmapFactory.decodeByteArray(image,0,image.length);
                iv.setImageBitmap(image1);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });



        TextView tv = findViewById(R.id.tv_nomeAttivitaDettaglio);
        tv.setText(att.getNomeAttivita());
        TextView tv2 = findViewById(R.id.tv_descrizioneAttivitaDettaglio);
        tv2.setText(att.getDescrizione());
        TextView tv3 = findViewById(R.id.costoPersona);
        tv3.setText("Il costo per persona è : "+att.getCostoPerPersona()+"€");

        Button btn = findViewById(R.id.btn_prenota);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PrenotaActivity.class);
                att.setImage("");
                i.putExtra("attivita", att);
                i.putExtra("user", user);
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
                                i.putExtra("user", user);
                                i.putExtra("tipologia", "Sportiva");
                                context.startActivity(i);
                            }else if(which == 1){
                                Intent i = new Intent(context, SearchActivity.class);
                                i.putExtra("user", user);
                                i.putExtra("tipologia", "Culturale");
                                context.startActivity(i);
                            }else if(which == 2){
                                Intent i = new Intent(context, SearchActivity.class);
                                i.putExtra("user", user);
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
                    i.putExtra("user", user);
                    context.startActivity(i);
                    return true;
                case R.id.navigation_profilo:
                    Intent in = new Intent(context, ProfiloActivity.class);
                    in.putExtra("user", user);
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

}

