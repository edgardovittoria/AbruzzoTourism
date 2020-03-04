package it.univaq.sose.domain;

import java.util.Date;
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
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "turista")
@XmlRootElement(name = "turista")
@XmlAccessorType(XmlAccessType.NONE)
public class Turista {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(namespace = "http://service.sose.univaq.it/")
	private int IDTurista;
	
	@Column
	@XmlElement(namespace = "http://service.sose.univaq.it/")
	private String Nome;
	
	@Column
	@XmlElement(namespace = "http://service.sose.univaq.it/")
	private String email;
	
	@Column
	@XmlElement(namespace = "http://service.sose.univaq.it/")
	private String password;
	
	@Column
	@XmlElement(namespace = "http://service.sose.univaq.it/")
	private Date dataNascita;

	public int getIDTurista() {
		return IDTurista;
	}


	public void setIDTurista(int iDTurista) {
		IDTurista = iDTurista;
	}

	public String getNome() {
		return this.Nome;
	}


	/**
	 * 
	 * @param Nome
	 */
	public void setNome(String Nome) {
		this.Nome = Nome;
	}

	public String getEmail() {
		return this.email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Date getDataNascita() {
		return this.dataNascita;
	}

	/**
	 * 
	 * @param dataNascita
	 */
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}


	

}