package univaq.aq.it.abruzzotourism.Activities.Signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.MainActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Prenotazione;
import univaq.aq.it.abruzzotourism.domain.Turista;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.utility.RESTClient;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class SigninActivity extends AppCompatActivity {

    Context context = this;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Button btn_signin = findViewById(R.id.signin);
        final EditText nome_cognome = findViewById(R.id.nome_signin);
        final EditText email_signin = findViewById(R.id.email_signin);
        final EditText password_signin = findViewById(R.id.password_signin);
        final EditText data_nascita = findViewById(R.id.data_nascita_signin);

        userLocalStore = new UserLocalStore(context);

        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            btn_signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        byte[] passwordbyte = md.digest(password_signin.getText().toString().getBytes());
                        String passwordEncoded = Base64.encodeToString(passwordbyte, 0);

                        final Turista turista = new Turista();
                        turista.setNome(nome_cognome.getText().toString());
                        turista.setEmail(email_signin.getText().toString());
                        turista.setPassword(passwordEncoded.substring(0,24));
                        turista.setDataNascita(data_nascita.getText().toString());
                        List<Prenotazione> prenotazioni = Collections.emptyList();

                        RequestParams requestParams = new RequestParams();
                        requestParams.put("email", turista.getEmail());
                        requestParams.put("nome", turista.getNome());
                        requestParams.put("password", turista.getPassword());
                        requestParams.put("dataNascita", turista.getDataNascita());

                        requestParams.setUseJsonStreamer(true);
                        requestParams.setElapsedFieldInJsonStreamer(null);

                        RESTClient.post("/signinTurista", requestParams, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Intent i = new Intent(context, MainActivity.class);
                                UserDetails user = new UserDetails(turista.getEmail(), password_signin.getText().toString(), "Turista");

                                userLocalStore.storeUserData(user);
                                userLocalStore.setUserLoggedIn(true);

                                context.startActivity(i);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            }
                        });


                }
            });

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
