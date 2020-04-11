package univaq.aq.it.abruzzotourism.Activities.ProfiloAttivita;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.Activities.Prenotazione.PrenotaActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.utility.RESTClient;

public class AggiungiPrenotazioneActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    List<Integer> values = new ArrayList<>();
    int numPartecipanti = 1;
    int annoSelezionato;
    int meseSelezionato;
    int giornoSelezioato;
    String dataSvolgimentoAttivita = "";
    String oraSelezionata;
    Attivita attivita = new Attivita();
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_prenotazione);

        attivita = getIntent().getParcelableExtra("attivita");

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner_aggiungi_prenotazione);
        spin.setOnItemSelectedListener(this);
        for(int i = numPartecipanti;i<16;i++){
            values.add(i);
        }
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_item,values);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        Spinner spin2 = (Spinner) findViewById(R.id.spinner2_aggiungi_prenotazione);
        spin2.setOnItemSelectedListener(this);

        String[] ore = new String[]{"9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00"};

        ArrayAdapter aa2 = new ArrayAdapter(this, R.layout.spinner_item, ore);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin2.setAdapter(aa2);

        Button btn_annulla = findViewById(R.id.button_annulla_aggiungi_prenotazione);
        btn_annulla.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PrenotaActivity.class);
                finish();
            }
        });

        final CalendarView calendarView = findViewById(R.id.calendarView_aggiungi_prenotazione);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                annoSelezionato = year;
                meseSelezionato = month+1;
                giornoSelezioato = dayOfMonth;
                dataSvolgimentoAttivita = giornoSelezioato+"/"+meseSelezionato+"/"+annoSelezionato+"-"+oraSelezionata;
            }
        });
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Spinner spinner = (Spinner) arg0;

        if(spinner.getId() == R.id.spinner_aggiungi_prenotazione){
            numPartecipanti = position + numPartecipanti ;
        }else if (spinner.getId() == R.id.spinner2_aggiungi_prenotazione){
            oraSelezionata = (String) arg0.getItemAtPosition(position);
            dataSvolgimentoAttivita = giornoSelezioato+"/"+meseSelezionato+"/"+annoSelezionato+"-"+oraSelezionata;
            Log.i("ora :", oraSelezionata);
        }
        Button btn_procedi = findViewById(R.id.btn_procedi_aggiungi_prenotazione);
        final EditText nome_e_cognome_prenotante = findViewById(R.id.nome_e_cognome);
        btn_procedi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                RequestParams requestParams = new RequestParams();

                requestParams.put("costo", numPartecipanti*attivita.getCostoPerPersona());
                requestParams.put("pagata", false);
                requestParams.put("confermata", true);

                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject2 = new JSONObject();
                try {
                    jsonObject.put("idattività", attivita.getIDAttivita());
                    jsonObject.put("nomeAttivita", attivita.getNomeAttivita());
                    jsonObject.put("costoPerPersona", attivita.getCostoPerPersona());
                    jsonObject.put("numMaxPartecipanti", attivita.getNumMaxPartecipanti());
                    jsonObject.put("image", attivita.getImage());
                    jsonObject.put("descrizione", attivita.getDescrizione());
                    jsonObject.put("tipologia", attivita.getTipologia());

                    jsonObject2.put("idutenteAttivita", attivita.getUtenteAttivita().getIDUtenteAttivita());
                    jsonObject2.put("nomeUtenteAttivita", attivita.getUtenteAttivita().getNomeUtenteAttivita());
                    jsonObject2.put("email", attivita.getUtenteAttivita().getEmail());
                    jsonObject2.put("password", attivita.getUtenteAttivita().getPassword());

                    jsonObject.put("utenteAttivita", jsonObject2);

                    requestParams.put("attivita", jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                requestParams.put("numPartecipanti", numPartecipanti);
                requestParams.put("dataDiPrenotazione", calendar.getTime().toString());
                requestParams.put("dataSvolgimentoAttivita", dataSvolgimentoAttivita);
                requestParams.put("prenotazioneANomeDi", nome_e_cognome_prenotante.getText().toString());
                requestParams.setUseJsonStreamer(true);
                requestParams.setElapsedFieldInJsonStreamer(null);

                RESTClient.post("/ProfiloAttivitaService/prenotazioni", requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String prenotazione_avvenuta = "La tua prenotazione è stata effettuata con successo!!!";
                        AlertDialog alertDialog = new AlertDialog.Builder(AggiungiPrenotazioneActivity.this).create();
                        alertDialog.setTitle("PREANOTAZIONE AVVENUTA");
                        alertDialog.setMessage(prenotazione_avvenuta);
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent i = new Intent(context, ProfiloAttivitaActivity.class);
                                        context.startActivity(i);
                                        finish();
                                    }
                                });
                        alertDialog.show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        String errore = "La prenotazione NON è stata registrata!!!Riprovare.";
                        Toast.makeText(context, errore, errore.length()).show();
                    }
                });

            }
        });


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}
}
