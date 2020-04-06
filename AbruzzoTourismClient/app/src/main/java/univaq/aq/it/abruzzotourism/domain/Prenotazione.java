package univaq.aq.it.abruzzotourism.domain;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Prenotazione implements KvmSerializable {


    private int IDPrenotazione;
    private String dataDiPrenotazione;
    private Turista TuristaPrenotante;
    private Attivita Attivita;
    private int numPartecipanti;
    private float costo;
    private Boolean confermata;
    private Boolean pagata;
    private String dataSvolgimentoAttivita;
    private String prenotazioneANomeDi;

    public Prenotazione() {}

    public int getIDPrenotazione() {
        return IDPrenotazione;
    }

    public void setIDPrenotazione(int IDPrenotazione) {
        this.IDPrenotazione = IDPrenotazione;
    }

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

    public String getPrenotazioneANomeDi() {
        return prenotazioneANomeDi;
    }

    public void setPrenotazioneANomeDi(String prenotazioneANomeDi) {
        this.prenotazioneANomeDi = prenotazioneANomeDi;
    }

    @Override
    public Object getProperty(int index) {
        switch (index)
        {

            case 0: return IDPrenotazione;
            case 1: return dataDiPrenotazione;
            case 2: return TuristaPrenotante;
            case 3: return Attivita;
            case 4: return numPartecipanti;
            case 5: return costo;
            case 6: return confermata;
            case 7: return pagata;
            case 8: return dataSvolgimentoAttivita;
            case 9: return prenotazioneANomeDi;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 10;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                IDPrenotazione = Integer.parseInt(value.toString());
                break;
            case 1:
                dataDiPrenotazione = value.toString();
                break;
            case 2:
                TuristaPrenotante = (Turista) value;
                break;
            case 3:
                Attivita = (Attivita) value;
                break;
            case 4:
                numPartecipanti = Integer.parseInt(value.toString());
                break;
            case 5:
                costo = Float.parseFloat(value.toString());
                break;
            case 6:
                confermata = Boolean.parseBoolean(value.toString());
                break;
            case 7:
                pagata = Boolean.parseBoolean(value.toString());
                break;
            case 8:
                dataSvolgimentoAttivita = value.toString();
                break;
            case 9:
                prenotazioneANomeDi = value.toString();
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
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "IDPrenotazione";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "dataDiPrenotazione";
                break;
            case 2:
                info.type = PropertyInfo.OBJECT_CLASS;
                info.name = "TuristaPrenotante";
                break;
            case 3:
                info.type = PropertyInfo.OBJECT_CLASS;
                info.name = "Attivita";
                break;
            case 4:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "numPartecipanti";
                break;
            case 5:
                info.type = Float.class;
                info.name = "costo";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "confermata";
                break;
            case 7:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "pagata";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "dataSvolgimentoAttivita";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "prenotazioneANomeDi";
                break;
            default:
                break;
        }
    }
}
