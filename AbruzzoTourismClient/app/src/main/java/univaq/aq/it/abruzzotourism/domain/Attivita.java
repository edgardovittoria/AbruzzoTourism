package univaq.aq.it.abruzzotourism.domain;

import android.os.Parcel;
import android.os.Parcelable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Attivita implements KvmSerializable, Parcelable {

    private int IDAttivita;
    private String nomeAttivita;
    private String descrizione;
    private float CostoPerPersona;
    private int NumMaxPartecipanti;
    private String image;
    private String tipologia;
    private UtenteAttivita utenteAttivita;

    public Attivita(){}

    public Attivita(int IDAttivita, String nomeAttivita, String descrizione, float CostoPerPersona, int numMaxPartecipanti, String image, String tipologia, UtenteAttivita utenteAttivita){
        this.IDAttivita = IDAttivita;
        this.nomeAttivita = nomeAttivita;
        this.descrizione = descrizione;
        this.CostoPerPersona = CostoPerPersona;
        this.NumMaxPartecipanti = numMaxPartecipanti;
        this.image = image;
        this.tipologia = tipologia;
        this.utenteAttivita = utenteAttivita;
    }

    protected Attivita(Parcel in) {
        IDAttivita = in.readInt();
        nomeAttivita = in.readString();
        descrizione = in.readString();
        CostoPerPersona = in.readFloat();
        NumMaxPartecipanti = in.readInt();
        image = in.readString();
        tipologia = in.readString();
        utenteAttivita = in.readTypedObject(UtenteAttivita.CREATOR);
    }

    public static final Creator<Attivita> CREATOR = new Creator<Attivita>() {
        @Override
        public Attivita createFromParcel(Parcel in) {
            return new Attivita(in);
        }

        @Override
        public Attivita[] newArray(int size) {
            return new Attivita[size];
        }
    };

    public int getIDAttivita() {
        return IDAttivita;
    }

    public void setIDAttivita(int IDAttivita) {
        this.IDAttivita = IDAttivita;
    }

    public String getNomeAttivita() {
        return nomeAttivita;
    }

    public void setNomeAttivita(String nomeAttivita) {
        this.nomeAttivita = nomeAttivita;
    }

    public float getCostoPerPersona() {
        return CostoPerPersona;
    }

    public void setCostoPerPersona(float costoPerPersona) {
        CostoPerPersona = costoPerPersona;
    }

    public int getNumMaxPartecipanti() {
        return NumMaxPartecipanti;
    }

    public void setNumMaxPartecipanti(int numMaxPartecipanti) {
        NumMaxPartecipanti = numMaxPartecipanti;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImage(){
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public UtenteAttivita getUtenteAttivita() {
        return utenteAttivita;
    }

    public void setUtenteAttivita(UtenteAttivita utenteAttivita) {
        this.utenteAttivita = utenteAttivita;
    }

    @Override
    public Object getProperty(int index) {
        switch (index)
        {
            case 0: return IDAttivita;
            case 1: return nomeAttivita;
            case 2: return descrizione;
            case 3: return CostoPerPersona;
            case 4: return NumMaxPartecipanti;
            case 5: return image;
            case 6: return tipologia;
            case 7: return utenteAttivita;

        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 8;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index)
        {
            case 0:
                IDAttivita = Integer.parseInt(value.toString());
                break;
            case 1:
                nomeAttivita = value.toString();
                break;
            case 2:
                descrizione = value.toString();
                break;
            case 3:
                CostoPerPersona = Float.parseFloat(value.toString());
                break;
            case 4:
                NumMaxPartecipanti = Integer.parseInt(value.toString());
                break;
            case 5:
                image = value.toString();
                break;
            case 6:
                tipologia = value.toString();
                break;
            case 7:
                utenteAttivita = (UtenteAttivita) value;
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
                info.name = "IDAttività";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "nomeAttivita";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "descrizione";
                break;
            case 3:
                info.type = Float.class;
                info.name = "CostoPerPersona";
                break;
            case 4:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "NumMaxPartecipanti";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "image";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "tipologia";
                break;
            case 7:
                info.type = PropertyInfo.OBJECT_CLASS;
                info.name = "utenteAttivita";
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
        dest.writeInt(IDAttivita);
        dest.writeString(nomeAttivita);
        dest.writeString(descrizione);
        dest.writeFloat(CostoPerPersona);
        dest.writeInt(NumMaxPartecipanti);
        dest.writeString(image);
        dest.writeString(tipologia);
        dest.writeParcelable(utenteAttivita, flags);
    }
}
