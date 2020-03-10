package univaq.aq.it.abruzzotourism.Activities.AggiungiAttivita.ProfiloAttivita;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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

import cz.msebera.android.httpclient.Header;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.UtenteAttivita;
import univaq.aq.it.abruzzotourism.utility.RESTClient;

public class ProfiloAttivitaActivity extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo_attivita);

        UtenteAttivita utenteAttivita = getIntent().getParcelableExtra("user");

        TextView nome_attivita = findViewById(R.id.tv_nome_profilo_attivita);
        nome_attivita.setText(utenteAttivita.getNomeUtenteAttivita());

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

        RESTClient.get("/getImageByName/" + utenteAttivita.getNomeUtenteAttivita(), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                byte[] imagebyte = Base64.decode(new String(responseBody), 0);
                Bitmap imagebitmap = BitmapFactory.decodeByteArray(imagebyte,0,imagebyte.length);
                ImageView image = findViewById(R.id.img_profilo_attivita);
                image.setImageBitmap(imagebitmap);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, error.getMessage(), error.getMessage().length()).show();
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

}
