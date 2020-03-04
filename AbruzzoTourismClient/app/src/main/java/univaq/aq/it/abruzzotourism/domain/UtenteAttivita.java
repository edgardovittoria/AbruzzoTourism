package univaq.aq.it.abruzzotourism.domain;

import android.os.Parcel;
import android.os.Parcelable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class UtenteAttivita implements KvmSerializable, Parcelable {

    private int IDUtenteAttivita;
    private String email;
    private String nomeUtenteAttivita;
    private String password;

    public  UtenteAttivita(String email, String nomeUtenteAttivita, String password){
        this.email = email;
        this.nomeUtenteAttivita = nomeUtenteAttivita;
        this.password = password;
    }

    protected UtenteAttivita(Parcel in) {
        IDUtenteAttivita = in.readInt();
        email = in.readString();
        nomeUtenteAttivita = in.readString();
        password = in.readString();
    }

    public static final Creator<UtenteAttivita> CREATOR = new Creator<UtenteAttivita>() {
        @Override
        public UtenteAttivita createFromParcel(Parcel in) {
            return new UtenteAttivita(in);
        }

        @Override
        public UtenteAttivita[] newArray(int size) {
            return new UtenteAttivita[size];
        }
    };

    public int getIDUtenteAttivita() {
        return IDUtenteAttivita;
    }

    public void setIDUtenteAttivita(int IDUtenteAttivita) {
        this.IDUtenteAttivita = IDUtenteAttivita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeUtenteAttivita() {
        return nomeUtenteAttivita;
    }

    public void setNomeUtenteAttivita(String nomeUtenteAttivita) {
        this.nomeUtenteAttivita = nomeUtenteAttivita;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object getProperty(int index) {
        switch (index)
        {
            case 0: return IDUtenteAttivita;
            case 1: return email;
            case 2: return nomeUtenteAttivita;
            case 3: return password;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void setProperty(int index, Object value) {

        switch (index)
        {
            case 0:
                IDUtenteAttivita = Integer.parseInt(value.toString());
                break;
            case 1:
                email = value.toString();
                break;
            case 2:
                nomeUtenteAttivita = value.toString();
                break;
            case 3:
                password = value.toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {

        switch (index) {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "IDUtenteAttivita";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "email";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "nomeUtenteAttivita";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "password";
                break;
            default:
                break;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IDUtenteAttivita);
        dest.writeString(email);
        dest.writeString(nomeUtenteAttivita);
        dest.writeString(password);
    }
}
