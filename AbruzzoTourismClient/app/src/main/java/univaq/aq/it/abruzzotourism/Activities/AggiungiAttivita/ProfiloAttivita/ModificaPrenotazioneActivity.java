package univaq.aq.it.abruzzotourism.Activities.AggiungiAttivita.ProfiloAttivita;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.Activities.Prenotazione.PrenotaActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.utility.RESTClient;

public class ModificaPrenotazioneActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    List<Integer> values = new ArrayList<>();
    int numPartecipanti;
    Float costoPerPersona;
    int annoSelezionato;
    int meseSelezionato;
    int giornoSelezioato;
    String oraSelezionata;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_prenotazione);

        numPartecipanti = getIntent().getIntExtra("numPartecipanti", 0);
        costoPerPersona = getIntent().getFloatExtra("costo", 0)/numPartecipanti;


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner_modifica);
        spin.setOnItemSelectedListener(this);
        for(int i = numPartecipanti;i<16;i++){
            values.add(i);
        }
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_item,values);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        Spinner spin2 = (Spinner) findViewById(R.id.spinner2_modifica);
        spin2.setOnItemSelectedListener(this);

        String[] ore = new String[]{"9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00"};

        ArrayAdapter aa2 = new ArrayAdapter(this, R.layout.spinner_item, ore);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin2.setAdapter(aa2);

        Button btn_annulla = findViewById(R.id.button_annulla_modifica);
        btn_annulla.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PrenotaActivity.class);
                finish();
            }
        });

        final CalendarView calendarView = findViewById(R.id.calendarView_modifica);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                annoSelezionato = year;
                meseSelezionato = month;
                giornoSelezioato = dayOfMonth;
            }
        });
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Spinner spinner = (Spinner) arg0;
        final int IDPrenotazione = getIntent().getIntExtra("IDPrenotazione", 0);

        if(spinner.getId() == R.id.spinner_modifica){
            numPartecipanti = position + numPartecipanti ;
        }else if (spinner.getId() == R.id.spinner2_modifica){
            oraSelezionata = (String) arg0.getItemAtPosition(position);
        }
        final String dataSvolgimentoAttivita = giornoSelezioato+"/"+meseSelezionato+"/"+annoSelezionato+"-"+oraSelezionata;
        Button btn_procedi = findViewById(R.id.btn_procedi_modifica);
        btn_procedi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                RequestParams requestParams = new RequestParams();
                requestParams.put("dataSvolgimentoAttivita", dataSvolgimentoAttivita);
                Log.i("numpart2", ""+costoPerPersona);
                requestParams.put("numPartecipanti", numPartecipanti);
                requestParams.put("costo", costoPerPersona*numPartecipanti);
                requestParams.setUseJsonStreamer(true);
                requestParams.setElapsedFieldInJsonStreamer(null);

                RESTClient.put("/prenotazioni/"+IDPrenotazione, requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String successo = "La modifica è avvenuta con successo!!!";
                        Toast.makeText(context, successo, successo.length()).show();
                        Intent i = new Intent(context, ProfiloAttivitaActivity.class);
                        context.startActivity(i);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        String errore = "La modifica NON è avvenuta con successo!!!Riprovare.";
                        Toast.makeText(context, errore, errore.length()).show();
                    }
                });
            }
        });


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}
}
