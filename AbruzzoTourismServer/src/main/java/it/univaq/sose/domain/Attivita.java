package it.univaq.sose.domain;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "attivita")
@XmlRootElement(name = "attivita")
@XmlAccessorType(XmlAccessType.NONE)
public class Attivita implements Serializable {

	private static final long serialVersionUID = -5920470663253795060L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private int IDAttività;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private String nomeAttivita;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private float CostoPerPersona;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private int NumMaxPartecipanti;

	@Column
	@Lob
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private String image;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private String descrizione;

	@OneToMany(mappedBy = "Attivita")
	@JsonIgnore
	private List<Prenotazione> prenotazioni;

	@Enumerated(EnumType.STRING)
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private TipologiaAttivita tipologia;

	@OneToOne
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private UtenteAttivita utenteAttivita;

	public int getIDAttività() {
		return this.IDAttività;
	}

	public void setIDAttività(int IDAttività) {
		this.IDAttività = IDAttività;
	}

	public String getNomeAttivita() {
		return nomeAttivita;
	}

	public void setNomeAttivita(String nomeAttivita) {
		this.nomeAttivita = nomeAttivita;
	}

	public float getCostoPerPersona() {
		return this.CostoPerPersona;
	}

	public void setCostoPerPersona(float CostoPerPersona) {
		this.CostoPerPersona = CostoPerPersona;
	}

	public int getNumMaxPartecipanti() {
		return this.NumMaxPartecipanti;
	}

	public void setNumMaxPartecipanti(int NumMaxPartecipanti) {
		this.NumMaxPartecipanti = NumMaxPartecipanti;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	public TipologiaAttivita getTipologia() {
		return tipologia;
	}

	public void setTipologia(TipologiaAttivita tipologia) {
		this.tipologia = tipologia;
	}

	public UtenteAttivita getUtenteAttivita() {
		return utenteAttivita;
	}

	public void setUtenteAttivita(UtenteAttivita utenteAttivita) {
		this.utenteAttivita = utenteAttivita;
	}

}