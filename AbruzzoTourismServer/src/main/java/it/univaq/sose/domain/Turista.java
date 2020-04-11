package it.univaq.sose.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "turista")
@XmlRootElement(name = "turista")
@XmlAccessorType(XmlAccessType.NONE)
public class Turista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private int IDTurista;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private String Nome;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private String email;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private String password;

	@Column
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private Date dataNascita;

	@Column
	@Lob
	@XmlElement(namespace = "http://soap.service.sose.univaq.it/")
	private String image;

	public int getIDTurista() {
		return IDTurista;
	}

	public void setIDTurista(int iDTurista) {
		IDTurista = iDTurista;
	}

	public String getNome() {
		return this.Nome;
	}

	public void setNome(String Nome) {
		this.Nome = Nome;
	}

	public String getEmail() {
		return this.email;
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

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}