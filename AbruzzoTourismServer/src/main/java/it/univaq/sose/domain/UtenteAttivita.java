package it.univaq.sose.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.opensaml.saml2.metadata.EmailAddress;


@Entity
@Table(name = "utenteAttivita")
@XmlRootElement(name = "utenteAttivita")
@XmlAccessorType(XmlAccessType.NONE)
public class UtenteAttivita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(namespace = "http://service.sose.univaq.it/")
	private int IDUtenteAttivita;
	
	@Column
	@XmlElement(namespace = "http://service.sose.univaq.it/")
	private String email;
	
	@Column
	@XmlElement(namespace = "http://service.sose.univaq.it/")
	private String nomeUtenteAttivita;
	
	@Column
	@XmlElement(namespace = "http://service.sose.univaq.it/")
	private String password;
	
	@OneToMany
	@XmlElement(namespace = "http://service.sose.univaq.it/")
	private List<Prenotazione> prenotazioni;

	public int getIDUtenteAttivita() {
		return IDUtenteAttivita;
	}

	public void setIDUtenteAttivita(int iDUtenteAttivita) {
		IDUtenteAttivita = iDUtenteAttivita;
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

	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	

}
