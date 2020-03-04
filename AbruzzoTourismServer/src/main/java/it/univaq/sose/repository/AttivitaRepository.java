package it.univaq.sose.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.annotations.common.util.impl.Log;

import it.univaq.sose.domain.Attivita;
import it.univaq.sose.domain.TipologiaAttivita;
import net.bytebuddy.asm.Advice.This;


public class AttivitaRepository {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");

	private EntityManager em = emf.createEntityManager();
	
	public void addAttivita(Attivita attivita) {

		try {
			this.em.getTransaction().begin();
			this.em.persist(attivita);
			this.em.getTransaction().commit();
		} catch (Exception e) {
			if (this.em.getTransaction() != null) {
				this.em.getTransaction().rollback();
			}
			e.printStackTrace();
		}

	}
	
	public Attivita getAttivta(int ID) {
		Attivita attivita = (Attivita) this.em.createQuery("select a from Attivita a where IDAttività="+ID).getResultList().get(0);
		return attivita;
	}
	
	public List<Attivita> getAttivtaTipologia(TipologiaAttivita tipologia) {
		List<Attivita> attivita = this.em.createQuery("select a from Attivita a where tipologia LIKE :tipologia").setParameter("tipologia", tipologia).getResultList();
		return attivita;
	}
	
	public Attivita getAttivitaNome(String nome) {
	Attivita attivita = (Attivita) this.em.createQuery("select a from Attivita a where nomeAttivita LIKE :nome").setParameter("nome", nome).getResultList().get(0);
	return attivita;
	}
	
	public List<Attivita> getAttivitaHome(){
		List<Attivita> attivita = this.em.createQuery("SELECT a FROM Attivita a").setMaxResults(10).getResultList();
		return attivita;
	}
	
	public String getImage(int ID) {
		String imageString = (String) this.em.createQuery("SELECT a.image FROM Attivita a WHERE IDAttività="+ID).getResultList().get(0);
		return imageString;
	}
}
