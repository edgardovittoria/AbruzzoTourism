package it.univaq.sose.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "prenotazione")
@XmlRootElement(name = "prenotazione")
@XmlAccessorType(XmlAccessType.NONE)
public class Prenotazione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private int IDPrenotazione;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private String dataDiPrenotazione;

	@ManyToOne
	@JoinColumn(name = "turista")
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private Turista TuristaPrenotante;

	@ManyToOne
	@JoinColumn(name = "attivita")
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private Attivita Attivita;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private int numPartecipanti;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private float costo;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private Boolean confermata;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private Boolean pagata;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private String dataSvolgimentoAttivita;
	
	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private String prenotazioneANomeDi;

	public Prenotazione() {
	}

	public Prenotazione(Turista turista) {
		this.TuristaPrenotante = turista;
	}

	public int getIDPrenotazione() {
		return this.IDPrenotazione;
	}

	public void setIDPrenotazione(int IDPrenotazione) {
		this.IDPrenotazione = IDPrenotazione;
	}

	public String getDataDiPrenotazione() {
		return dataDiPrenotazione;
	}

	public void setDataDiPrenotazione(String dataDiPrenotazione) {
		this.dataDiPrenotazione = dataDiPrenotazione;
	}

	public Turista getTuristaPrenotante() {
		return TuristaPrenotante;
	}

	public void setTuristaPrenotante(Turista turistaPrenotante) {
		TuristaPrenotante = turistaPrenotante;
	}

	public Attivita getAttivita() {
		return Attivita;
	}

	public void setAttivita(Attivita attivita) {
		Attivita = attivita;
	}

	public int getNumPartecipanti() {
		return this.numPartecipanti;
	}

	public void setNumPartecipanti(int numPartecipanti) {
		this.numPartecipanti = numPartecipanti;
	}

	public float getCosto() {
		return this.costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public Boolean getConfermata() {
		return this.confermata;
	}

	public void setConfermata(Boolean confermata) {
		this.confermata = confermata;
	}

	public Boolean getPagata() {
		return this.pagata;
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
	
	

}