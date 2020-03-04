package univaq.aq.it.abruzzotourism.domain;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Prenotazione implements KvmSerializable {


    private String dataDiPrenotazione;
    private Turista TuristaPrenotante;
    private Attivita Attivita;
    private int numPartecipanti;
    private float costo;
    private Boolean confermata;
    private Boolean pagata;
    private String dataSvolgimentoAttivita;

    public Prenotazione() {}

    public Prenotazione(Turista turista) {
        this.TuristaPrenotante = turista;
    }

    public String getDataDiPrenotazione() { return dataDiPrenotazione; }

    public void setDataDiPrenotazione(String data) { this.dataDiPrenotazione = data; }

    public Turista getTuristaPrenotante() {
        return TuristaPrenotante;
    }

    public void setTuristaPrenotante(Turista turistaPrenotante) {
        TuristaPrenotante = turistaPrenotante;
    }

    public Attivita getAttivita() {
        return Attivita;
    }

    public void setAttivita(univaq.aq.it.abruzzotourism.domain.Attivita attivita) {
        Attivita = attivita;
    }

    public int getNumPartecipanti() {
        return numPartecipanti;
    }

    public void setNumPartecipanti(int numPartecipanti) {
        this.numPartecipanti = numPartecipanti;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public Boolean getConfermata() {
        return confermata;
    }

    public void setConfermata(Boolean confermata) {
        this.confermata = confermata;
    }

    public Boolean getPagata() {
        return pagata;
    }

    public void setPagata(Boolean pagata) {
        this.pagata = pagata;
    }

    public String getDataSvolgimentoAttivita() {
        return dataSvolgimentoAttivita;
    }

    public void setDataSvolgimentoAttivita(String dataSvolgimentoAttivita) {
        this.dataSvolgimentoAttivita = dataSvolgimentoAttivita;
    }

    @Override
    public Object getProperty(int index) {
        switch (index)
        {

            case 0: return dataDiPrenotazione;
            case 1: return TuristaPrenotante;
            case 2: return Attivita;
            case 3: return numPartecipanti;
            case 4: return costo;
            case 5: return confermata;
            case 6: return pagata;
            case 7: return dataSvolgimentoAttivita;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 8;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                dataDiPrenotazione = value.toString();
                break;
            case 1:
                TuristaPrenotante = (Turista) value;
                break;
            case 2:
                Attivita = (Attivita) value;
                break;
            case 3:
                numPartecipanti = Integer.parseInt(value.toString());
                break;
            case 4:
                costo = Float.parseFloat(value.toString());
                break;
            case 5:
                confermata = Boolean.parseBoolean(value.toString());
                break;
            case 6:
                pagata = Boolean.parseBoolean(value.toString());
                break;
            case 7:
                dataSvolgimentoAttivita = value.toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        switch (index)
        {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "dataDiPrenotazione";
                break;
            case 1:
                info.type = PropertyInfo.OBJECT_CLASS;
                info.name = "TuristaPrenotante";
                break;
            case 2:
                info.type = PropertyInfo.OBJECT_CLASS;
                info.name = "Attivita";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "numPartecipanti";
                break;
            case 4:
                info.type = Float.class;
                info.name = "costo";
                break;
            case 5:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "confermata";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "pagata";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "dataSvolgimentoAttivita";
                break;
            default:
                break;
        }
    }
}
