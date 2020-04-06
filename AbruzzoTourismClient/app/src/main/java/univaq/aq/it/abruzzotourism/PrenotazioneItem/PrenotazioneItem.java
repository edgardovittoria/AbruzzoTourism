package univaq.aq.it.abruzzotourism.PrenotazioneItem;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */

  
  public class PrenotazioneItem {

    private String id;
    private int IDPrenotazione;
    private String dataSvolgimentoAttivita;
    private int numPartecipanti;
    private float costo;
    public String content;
    private String details;

    public PrenotazioneItem() {}

    public String getId() {
        return id;
    }

    public int getIDPrenotazione(){ return IDPrenotazione; }

    public String getDataSvolgimentoAttivita() {
        return dataSvolgimentoAttivita;
    }

    public int getNumPartecipanti() {
        return numPartecipanti;
    }

    public float getCosto() {
        return costo;
    }

    public String getContent() {
        return content;
    }

    public String getDetails() {
        return details;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIDPrenotazione(int IDPrenotazione) { this.IDPrenotazione = IDPrenotazione; }

    public void setDataSvolgimentoAttivita(String dataSvolgimentoAttivita) {
        this.dataSvolgimentoAttivita = dataSvolgimentoAttivita;
    }

    public void setNumPartecipanti(int numPartecipanti) {
        this.numPartecipanti = numPartecipanti;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
      public String toString() {
            return content;
        }
  }
