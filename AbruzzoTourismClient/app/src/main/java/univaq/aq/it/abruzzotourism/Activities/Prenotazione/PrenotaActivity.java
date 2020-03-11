package univaq.aq.it.abruzzotourism.Activities.Prenotazione;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import univaq.aq.it.abruzzotourism.MainActivity;
import univaq.aq.it.abruzzotourism.Activities.ProfiloTurista.ProfiloActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class PrenotaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Integer[] values = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    int numPersone = 1;
    int annoSelezionato;
    int meseSelezionato;
    int giornoSelezioato;
    String oraSelezionata;
    Context context = this;
    UserLocalStore userLocalStore;
    Attivita attivita = new Attivita();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenota);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        userLocalStore = new UserLocalStore(context);

        this.attivita = getIntent().getParcelableExtra("attivita");

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_item,values);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        spin2.setOnItemSelectedListener(this);

        String[] ore = new String[]{"8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00"};

        ArrayAdapter aa2 = new ArrayAdapter(this, R.layout.spinner_item, ore);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin2.setAdapter(aa2);

        TextView tv = findViewById(R.id.costo_totale);
        tv.setText("il costo totale è : "+ getIntent().getFloatExtra("costo",0)+"€");
        Button btn_annulla = findViewById(R.id.button_annulla1);
        btn_annulla.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PrenotaActivity.class);
                finish();
            }
        });

        final CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                annoSelezionato = year;
                meseSelezionato = month;
                giornoSelezioato = dayOfMonth;
            }
        });


        Button btn_procedi = findViewById(R.id.btn_procedi1);
        btn_procedi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                    Intent i = new Intent(context, MetodoDiPagamentoActivity.class);
                    i.putExtra("attivita", attivita);
                    //i.putExtra("user", getIntent().getParcelableExtra("user"));
                    i.putExtra("costoTotale", attivita.getCostoPerPersona()*numPersone);
                    i.putExtra("data", giornoSelezioato+"/"+meseSelezionato+"/"+annoSelezionato+"-"+oraSelezionata);

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
                                //i.putExtra("user", getIntent().getParcelableExtra("user"));
                                i.putExtra("tipologia", "Sportiva");
                                context.startActivity(i);
                            }else if(which == 1){
                                Intent i = new Intent(context, SearchActivity.class);
                                //i.putExtra("user", getIntent().getParcelableExtra("user"));
                                i.putExtra("tipologia", "Culturale");
                                context.startActivity(i);
                            }else if(which == 2){
                                Intent i = new Intent(context, SearchActivity.class);
                                //i.putExtra("user", getIntent().getParcelableExtra("user"));
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
                    //i.putExtra("user", getIntent().getParcelableExtra("user"));
                    context.startActivity(i);
                    return true;
                case R.id.navigation_profilo:
                    Intent in = new Intent(context, ProfiloActivity.class);
                    //in.putExtra("user", getIntent().getParcelableExtra("user"));
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


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Spinner spinner = (Spinner) arg0;

        if(spinner.getId() == R.id.spinner){
            numPersone = position + 1;
            TextView tv = findViewById(R.id.costo_totale);
            tv.setText("il costo totale è : "+ attivita.getCostoPerPersona()*numPersone+"€");
        }else{
            oraSelezionata = (String) arg0.getItemAtPosition(position);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}



}
