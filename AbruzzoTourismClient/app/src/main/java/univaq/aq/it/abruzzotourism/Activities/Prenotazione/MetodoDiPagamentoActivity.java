package univaq.aq.it.abruzzotourism.Activities.Prenotazione;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import univaq.aq.it.abruzzotourism.Activities.Search.SearchActivity;
import univaq.aq.it.abruzzotourism.Activities.Home.MainActivity;
import univaq.aq.it.abruzzotourism.Activities.ProfiloTurista.ProfiloActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class MetodoDiPagamentoActivity extends AppCompatActivity {

    Context context = this;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_di_pagamento);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        userLocalStore = new UserLocalStore(context);


        Button btn_annulla = findViewById(R.id.button_annulla2);
        btn_annulla.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PrenotaActivity.class);
                finish();
            }
        });

        final RadioGroup btn_pagamento = findViewById(R.id.radio_group);
        Button btn_procedi = findViewById(R.id.btn_procedi2);
        btn_procedi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                RadioButton radioButton = findViewById(btn_pagamento.getCheckedRadioButtonId());
                if(radioButton.getText().equals("Pagamento in sede")){
                    Intent i = new Intent(context, RiepilogoPrenotazioneActivity.class);
                    i.putExtra("attivita", getIntent().getParcelableExtra("attivita"));
                    i.putExtra("costoTotale", getIntent().getFloatExtra("costoTotale",0));
                    i.putExtra("data", getIntent().getStringExtra("data"));
                    context.startActivity(i);
                }
                else{

                }
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
}
