package univaq.aq.it.abruzzotourism;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfiloActivity extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

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
                                i.putExtra("user", getIntent().getParcelableExtra("user"));
                                i.putExtra("tipologia", "Sportiva");
                                context.startActivity(i);
                            }else if(which == 1){
                                Intent i = new Intent(context, SearchActivity.class);
                                i.putExtra("user", getIntent().getParcelableExtra("user"));
                                i.putExtra("tipologia", "Culturale");
                                context.startActivity(i);
                            }else if(which == 2){
                                Intent i = new Intent(context, SearchActivity.class);
                                i.putExtra("user", getIntent().getParcelableExtra("user"));
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
                    i.putExtra("user", getIntent().getParcelableExtra("user"));
                    context.startActivity(i);
                    return true;
                case R.id.navigation_profilo:
                    Intent in = new Intent(context, ProfiloActivity.class);
                    in.putExtra("user", getIntent().getParcelableExtra("user"));
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
