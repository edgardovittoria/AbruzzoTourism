package univaq.aq.it.abruzzotourism.Activities.AggiungiAttivita.ProfiloAttivita;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.login.Login;
import univaq.aq.it.abruzzotourism.utility.RESTClient;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class ProfiloAttivitaActivity extends AppCompatActivity {

    Context context = this;
    UserLocalStore userLocalStore;

    private static final int GALLERY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo_attivita);

        userLocalStore = new UserLocalStore(context);

        final UserDetails userDetails = userLocalStore.getLoggedInUser();

        RequestParams requestParams = new RequestParams();
        requestParams.put("email", userDetails.getEmail());
        requestParams.put("password", userDetails.getPassword());
        requestParams.setUseJsonStreamer(true);
        requestParams.setElapsedFieldInJsonStreamer(null);

        RESTClient.post("/loginUtenteAttivita", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                if (result.equals("true")){
                    /*se il login va a buon fine allora viene fatta la chiamata rest per recuperare l'attività
                    * che ci servirà per settare tutti i parametri riguardant l'interfaccia(Immagine e NomeAttivita)*/
                    RESTClient.get("/AttivitaByEmail/" + userDetails.getEmail(), null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                //recupero l'ggetto json ricevuto come risposta
                                JSONObject jsonObject = new JSONObject(new String(responseBody));
                                //creo l'attività grazie all'oggetto json
                                final Attivita attivita = new Attivita();
                                attivita.setIDAttivita(jsonObject.getInt("idattività"));
                                attivita.setNomeAttivita(jsonObject.getString("nomeAttivita"));
                                attivita.setTipologia(jsonObject.getString("tipologia"));
                                attivita.setDescrizione(jsonObject.getString("descrizione"));
                                attivita.setNumMaxPartecipanti(jsonObject.getInt("numMaxPartecipanti"));
                                attivita.setCostoPerPersona(Float.parseFloat(jsonObject.get("costoPerPersona").toString()));
                                attivita.setImage(jsonObject.getString("image"));
                                //viene settata l'immagine

                                byte[] imagebyte = Base64.decode(attivita.getImage(), 0);
                                Bitmap imagebitmap = BitmapFactory.decodeByteArray(imagebyte,0,imagebyte.length);
                                ImageView image = findViewById(R.id.img_profilo_attività);
                                image.setImageBitmap(imagebitmap);
                                //viene settato il nomeAttivita
                                TextView nome_attivita = findViewById(R.id.tv_nome_profilo_attivita);
                                nome_attivita.setText(attivita.getNomeAttivita());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ImageView image = findViewById(R.id.img_profilo_attività);
                            Bitmap bm = ((BitmapDrawable) image.getDrawable()).getBitmap();
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            String image1 = Base64.encodeToString(byteArray, 0);
                            Log.i("image0", image1);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(context, error.getMessage(), error.getMessage().length()).show();
                        }
                    });
                }else{

                    String errore = "Email o Password ERRATI!!!Riprovare.";
                    Toast.makeText(context, errore, errore.length()).show();
                    Intent i = new Intent(context, Login.class);
                    context.startActivity(i);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, error.getMessage(), error.getMessage().length()).show();
            }
        });


        FloatingActionButton cambia_immagine = findViewById(R.id.floatingActionButtonCambiaImmaginettivita);
        cambia_immagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });



        FragmentManager fm = getSupportFragmentManager();
        final Fragment fragment = fm.findFragmentById(R.id.fragment_profilo_attivita);
        hideFrangment(fragment);

        final View divider = findViewById(R.id.divider14);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton_profilo_attivita);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment.isHidden()){
                    showFrangment(fragment);
                    divider.setVisibility(View.VISIBLE);
                }else {
                    hideFrangment(fragment);
                    divider.setVisibility(View.INVISIBLE);

                }

            }
        });

        FloatingActionButton floatingActionButtonLogout = findViewById(R.id.floatingActionButtonLogoutAttivita);
        floatingActionButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLocalStore.setUserLoggedIn(false);
                userLocalStore.clearUserData();
                Intent i = new Intent(context, Login.class);
                context.startActivity(i);
            }
        });



    }

    public void hideFrangment(Fragment fragment){
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // hide the Fragment
        fragmentTransaction.hide(fragment);
        // save the changes
        fragmentTransaction.commit();
    }

    public void showFrangment(Fragment fragment){
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // hide the Fragment
        fragmentTransaction.show(fragment);
        // save the changes
        fragmentTransaction.commit();
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
                    //data.getData restituisce l'URI dellimmagine selezionata
                    Uri selectedImage = data.getData();
                    ImageView imageView = findViewById(R.id.img_profilo_attività);
                    imageView.setImageURI(selectedImage);

                    //viene ricavata l'immagine appena cambiata e convertita in byte per poi essere convertita in stringa
                    Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    TextView textView = findViewById(R.id.tv_nome_profilo_attivita);

                    /*l'immagine viene convertita in stringa per poter essere salvata nel db
                     *inoltre viene ricavato il nome dell'attivita per cui bisogna cambiare l'immagine*/
                    String image = Base64.encodeToString(byteArray, 0);
                    String nomeAttivita = textView.getText().toString();
                    Log.i("image1", ""+image.length());

                    Attivita attivita = new Attivita();
                    attivita.setImage(image);

                    /*l'immagine viene inserita nel corpo della richiesta http*/
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("image", attivita.getImage());


                    requestParams.setUseJsonStreamer(true);
                    requestParams.setElapsedFieldInJsonStreamer(null);

                    /*viene effattuata la chiamata REST al metodo put http per cambiare l'immagine
                    * il nome dell'attivita viene passato come parametro nell'url*/
                    RESTClient.put("/cambiaImmagine/" + nomeAttivita, requestParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String avviso = "L'Immagine è stata cambiata correttamente!!!";
                            Toast.makeText(context, avviso, avviso.length()).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(context, error.getMessage(), error.getMessage().length()).show();
                        }
                    });
                    break;
            }
    }

}
