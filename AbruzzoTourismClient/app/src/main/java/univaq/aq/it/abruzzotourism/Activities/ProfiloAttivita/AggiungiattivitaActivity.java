package univaq.aq.it.abruzzotourism.Activities.ProfiloAttivita;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.UtenteAttivita;
import univaq.aq.it.abruzzotourism.utility.RESTClient;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class AggiungiattivitaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int GALLERY_REQUEST_CODE = 1;
    String[] values = {"Sportiva", "Culturale", "FloraEFauna"};
    String tipologia = "";
    Context context = this;
    UserLocalStore userLocalStore;
    static UtenteAttivita utenteAttivita = new UtenteAttivita();

    public static UtenteAttivita getUtenteattivita(){
        return utenteAttivita;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungiattivita);

        userLocalStore = new UserLocalStore(context);

        utenteAttivita = getIntent().getParcelableExtra("user");


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner3);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_item,values);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        Button btn_carica_immagine = findViewById(R.id.btn_carica_immagine);
        btn_carica_immagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        final EditText costo = findViewById(R.id.et_costo);
        final EditText numMaxPers = findViewById(R.id.et_numMaxPers);
        final EditText descrizione = findViewById(R.id.et_descrizione);

        Button btn_crea_attivita = findViewById(R.id.btn_crea_attivita);
        btn_crea_attivita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressBar progressBar = findViewById(R.id.progressBarAggiungiAttivita);
                progressBar.setIndeterminate(true);

                //viene presa l'immagine e convertita in stringa per poter essere salvata nel db
                ImageView imageView = findViewById(R.id.img_take);
                Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                String image = Base64.encodeToString(byteArray, 0);

                //viene creata l'attività e vengono settati i relativi attributi
                Attivita attivita = new Attivita();
                String nomeAttivita = utenteAttivita.getNomeUtenteAttivita();
                attivita.setNomeAttivita(nomeAttivita);
                attivita.setImage(image);
                attivita.setCostoPerPersona(Float.parseFloat(costo.getText().toString()));
                attivita.setNumMaxPartecipanti(Integer.parseInt(numMaxPers.getText().toString()));
                attivita.setDescrizione(descrizione.getText().toString());
                attivita.setTipologia(tipologia);

                //viene creato l'oggetto json da passare nel corpo della richiesta
                RequestParams requestParams = new RequestParams();
                requestParams.put("nomeAttivita", attivita.getNomeAttivita());
                requestParams.put("costoPerPersona", attivita.getCostoPerPersona());
                requestParams.put("numMaxPartecipanti", attivita.getNumMaxPartecipanti());
                requestParams.put("image", attivita.getImage());
                requestParams.put("descrizione", attivita.getDescrizione());
                requestParams.put("tipologia", attivita.getTipologia());

                requestParams.setUseJsonStreamer(true);
                requestParams.setElapsedFieldInJsonStreamer(null);

                //invocazione del metodo REST creaAttivita
                RESTClient.post("/ProfiloAttivitaService/attivita/"+utenteAttivita.getEmail(), requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        if(response.equals("true")){
                            progressBar.setIndeterminate(false);
                            String prenotazione_avvenuta = "La tua Attività è stata registrata con successo!!!";
                            AlertDialog alertDialog = new AlertDialog.Builder(AggiungiattivitaActivity.this).create();
                            alertDialog.setTitle("REGISTRAZIONE AVVENUTA");
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
                        else{

                            String registrazione_fallita = "REGISTRAZIONE FALLITA";
                            Toast.makeText(context, registrazione_fallita, registrazione_fallita.length()).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        String registrazione_fallita = "ERRORE";
                        Toast.makeText(context, registrazione_fallita+" "+error, registrazione_fallita.length()).show();
                    }
                });


            }
        });
    }


    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png", "image/jpg"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    //@Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    ImageView image = findViewById(R.id.img_take);
                    image.setImageURI(selectedImage);
                    break;
            }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tipologia = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
