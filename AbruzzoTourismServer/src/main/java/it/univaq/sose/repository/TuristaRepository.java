package it.univaq.sose.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.univaq.sose.domain.Turista;

public class TuristaRepository {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");

	private EntityManager em = emf.createEntityManager();

	public boolean addTurista(Turista turista) {
		//turista.setNuovaPrenotazione(null);

		try {
			this.em.getTransaction().begin();
			this.em.persist(turista);
			this.em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (this.em.getTransaction() != null) {
				this.em.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}

	}
	
	public Turista getTuristaFromID(int ID) {
		Turista turista = (Turista) this.em.createQuery("select t from Turista t  where IDTurista="+ ID).getResultList().get(0);
		return turista;
	}
	
	public Turista getTuristaFromEmail(String email) {
		Turista turista = (Turista) this.em.createQuery("select t from Turista t  where email LIKE :email").setParameter("email", email).getResultList().get(0);
		return turista;
	}
}
