package univaq.aq.it.abruzzotourism.Activities.ProfiloTurista;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.Activities.Search.SearchActivity;
import univaq.aq.it.abruzzotourism.Activities.Home.MainActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Turista;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.Activities.login.Login;
import univaq.aq.it.abruzzotourism.utility.RESTClient;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class ProfiloActivity extends AppCompatActivity {

    Context context = this;
    UserLocalStore userLocalStore;
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        userLocalStore = new UserLocalStore(context);
        UserDetails userDetails = userLocalStore.getLoggedInUser();

        final ProgressBar progressBar = findViewById(R.id.progressBarProfiloTurista);
        progressBar.setIndeterminate(true);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fm = getSupportFragmentManager();
        final Fragment fragment = fm.findFragmentById(R.id.fragment_prenotazioni);
        hideFrangment(fragment);

        final View divider = findViewById(R.id.divider2);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
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

        FloatingActionButton floatingActionButtonLogout = findViewById(R.id.floatingActionButtonLogout);
        floatingActionButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLocalStore.setUserLoggedIn(false);
                userLocalStore.clearUserData();
                Intent i = new Intent(context, Login.class);
                context.startActivity(i);
            }
        });

        RequestParams requestParams = new RequestParams();
        requestParams.put("email", userDetails.getEmail());
        RESTClient.get("/ProfiloTuristaService/turisti/", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    progressBar.setIndeterminate(false);

                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    Turista turista = new Turista();
                    turista.setNome(jsonObject.getString("nome"));
                    turista.setDataNascita(jsonObject.getString("dataNascita"));
                    turista.setEmail(jsonObject.getString("email"));
                    turista.setIDTurista(jsonObject.getInt("idturista"));
                    turista.setPassword(jsonObject.getString("password"));
                    turista.setImage(jsonObject.getString("image"));

                    //viene settata l'immagine
                    ImageView imageProfilo = findViewById(R.id.img_profilo_turista);
                    byte[] imagebyte = Base64.decode(turista.getImage(), 0);
                    Bitmap imagebitmap = BitmapFactory.decodeByteArray(imagebyte,0,imagebyte.length);
                    imageProfilo.setImageBitmap(imagebitmap);

                    //viene settato il nome del turista
                    TextView nomeTurista = findViewById(R.id.tv_nome_turista);
                    nomeTurista.setText(turista.getNome());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        FloatingActionButton floatingActionButtonCambiaImmagine = findViewById(R.id.floatingActionButtonCambiaImmagineTurista);
        floatingActionButtonCambiaImmagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
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
                    //data.getData restituisce l'URI dellimmagine selezionata
                    Uri selectedImage = data.getData();
                    ImageView imageView = findViewById(R.id.img_profilo_turista);
                    imageView.setImageURI(selectedImage);

                    //viene ricavata l'immagine appena cambiata e convertita in byte per poi essere convertita in stringa
                    Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    /*l'immagine viene convertita in stringa per poter essere salvata nel db
                     *inoltre viene ricavato il nome dell'attivita per cui bisogna cambiare l'immagine*/
                    String image = Base64.encodeToString(byteArray, 0);

                    TextView textView = findViewById(R.id.tv_nome_turista);

                    Turista turista = new Turista();
                    turista.setNome(textView.getText().toString());
                    turista.setImage(image);

                    /*l'immagine viene inserita nel corpo della richiesta http*/
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("image", turista.getImage());


                    requestParams.setUseJsonStreamer(true);
                    requestParams.setElapsedFieldInJsonStreamer(null);


                    /*viene effattuata la chiamata REST al metodo put http per cambiare l'immagine
                     * il nome dell'attivita viene passato come parametro*/
                    RESTClient.put("/ProfiloTuristaService/turisti/"+turista.getNome(), requestParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String result = new String(responseBody);
                            if(result.equals("true")){
                                String avviso = "L'Immagine è stata cambiata correttamente!!!";
                                Toast.makeText(context, avviso, avviso.length()).show();
                            }else{
                                String avviso = "L'Immagine NOM è stata cambiata correttamente!!!";
                                Toast.makeText(context, avviso, avviso.length()).show();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(context, error.getMessage(), error.getMessage().length()).show();
                        }
                    });
                    break;
            }
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

}
