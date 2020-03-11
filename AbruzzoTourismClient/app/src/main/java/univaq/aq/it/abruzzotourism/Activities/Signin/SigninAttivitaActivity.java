package univaq.aq.it.abruzzotourism.Activities.Signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.Activities.AggiungiAttivita.AggiungiattivitaActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.domain.UtenteAttivita;
import univaq.aq.it.abruzzotourism.utility.RESTClient;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class SigninAttivitaActivity extends AppCompatActivity {

    Context context = this;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_attivita);

        userLocalStore = new UserLocalStore(context);

        Button btn_signin_attivita = findViewById(R.id.attivita_signin);
        final EditText email = findViewById(R.id.attivita_email_signin);
        final EditText nome = findViewById(R.id.nome_attivita_signin);
        final EditText password = findViewById(R.id.attivita_password_signin);

        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            btn_signin_attivita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        final String passwordMD5 = Base64.encodeToString(md.digest(password.getText().toString().getBytes("UTF-8")),0);

                        final UtenteAttivita utenteAttivita = new UtenteAttivita(email.getText().toString(), nome.getText().toString(), passwordMD5.substring(0,24));



                        RequestParams requestParams = new RequestParams();
                        requestParams.put("email", utenteAttivita.getEmail());
                        requestParams.put("nomeUtenteAttivita", utenteAttivita.getNomeUtenteAttivita());
                        requestParams.put("password", utenteAttivita.getPassword());


                        requestParams.setUseJsonStreamer(true);
                        requestParams.setElapsedFieldInJsonStreamer(null);



                        RESTClient.post("/signinUtenteAttivita", requestParams, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Toast.makeText(getApplicationContext(), "registrazione Avvenuta!!!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(context, AggiungiattivitaActivity.class);

                                UserDetails userDetails = new UserDetails(utenteAttivita.getEmail(),utenteAttivita.getPassword(),"Attivita");
                                userLocalStore.storeUserData(userDetails);
                                userLocalStore.setUserLoggedIn(true);
                                i.putExtra("user", utenteAttivita);
                                context.startActivity(i);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                }
            });

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
