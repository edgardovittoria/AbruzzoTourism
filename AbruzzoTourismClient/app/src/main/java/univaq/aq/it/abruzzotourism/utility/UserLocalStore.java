package univaq.aq.it.abruzzotourism.utility;

import android.content.Context;
import android.content.SharedPreferences;

import univaq.aq.it.abruzzotourism.domain.UserDetails;

public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(UserDetails userDetails){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("email", userDetails.getEmail());
        spEditor.putString("password", userDetails.getPassword());
        spEditor.putString("tipologia", userDetails.getTipologia());
        spEditor.commit();
    }

    public UserDetails getLoggedInUser(){
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");
        String tipologia = userLocalDatabase.getString("tipologia", "");

        UserDetails storeduser = new UserDetails(email, password, tipologia);
        return storeduser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
