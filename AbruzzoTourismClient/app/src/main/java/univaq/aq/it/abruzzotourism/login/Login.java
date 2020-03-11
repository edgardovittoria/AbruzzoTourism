package univaq.aq.it.abruzzotourism.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import univaq.aq.it.abruzzotourism.Activities.AggiungiAttivita.ProfiloAttivita.ProfiloAttivitaActivity;
import univaq.aq.it.abruzzotourism.Activities.Signin.SigninActivity;
import univaq.aq.it.abruzzotourism.Activities.Signin.SigninAttivitaActivity;
import univaq.aq.it.abruzzotourism.MainActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.UserDetails;
import univaq.aq.it.abruzzotourism.utility.UserLocalStore;

public class Login extends AppCompatActivity {

    Context context = this;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button loginAttivitaButton = findViewById(R.id.login_attivita);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button signinButton = findViewById(R.id.button_signin);
        final TextView registraAttivita = findViewById(R.id.signinAttivita);

        userLocalStore = new UserLocalStore(context);

        loginButton.setEnabled(true);
        loginAttivitaButton.setEnabled(true);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //viene "criptata la password per essere salvata nella sessione"
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    String passwordMD5 = Base64.encodeToString(md.digest(passwordEditText.getText().toString().getBytes("UTF-8")),0);
                    //viene creato l'utente da salvare nella sessione
                    UserDetails userDetails = new UserDetails(usernameEditText.getText().toString(), passwordMD5.substring(0,24), "Turista");
                    //viene salvato l'utente nella sessione
                    userLocalStore.storeUserData(userDetails);
                    userLocalStore.setUserLoggedIn(true);

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(context, MainActivity.class);
                //i.putExtra("user", userDetails);
                context.startActivity(i);
            }
        });

        loginAttivitaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //viene "criptata la password per essere salvata nella sessione"
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    String passwordMD5 = Base64.encodeToString(md.digest(passwordEditText.getText().toString().getBytes("UTF-8")),0);
                    //viene creato l'utente da salvare nella sessione
                    UserDetails userDetails = new UserDetails(usernameEditText.getText().toString(), passwordMD5.substring(0,24), "Attivita");
                    //viene salvato l'utente nella sessione
                    userLocalStore.storeUserData(userDetails);
                    userLocalStore.setUserLoggedIn(true);

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(context, ProfiloAttivitaActivity.class);
                context.startActivity(i);
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SigninActivity.class);
                context.startActivity(i);
            }
        });

        registraAttivita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SigninAttivitaActivity.class);
                context.startActivity(i);
            }
        });


    }




}
