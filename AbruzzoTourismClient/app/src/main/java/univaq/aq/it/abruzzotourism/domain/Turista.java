package univaq.aq.it.abruzzotourism.domain;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Turista implements KvmSerializable {

    private int IDTurista;
    private String Nome;
    private String email;
    private String password;
    private String dataNascita;

    public int getIDTurista() {
        return IDTurista;
    }

    public void setIDTurista(int IDTurista) {
        this.IDTurista = IDTurista;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    @Override
    public Object getProperty(int index) {
        switch (index)
        {
            case 0: return IDTurista;
            case 1: return Nome;
            case 2: return email;
            case 3: return password;
            case 4: return dataNascita;

        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 5;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index)
        {
            case 0:
                IDTurista = Integer.parseInt(value.toString());
                break;
            case 1:
                Nome = value.toString();
                break;
            case 2:
                email = value.toString();
                break;
            case 3:
                password = value.toString();
                break;
            case 4:
                dataNascita = value.toString();
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
                info.name = "IDTurista";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Nome";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "email";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "password";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "dataNascita";
                break;
        }
    }
}
