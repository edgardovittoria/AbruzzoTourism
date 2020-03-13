package it.univaq.sose.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.univaq.sose.domain.Turista;

public class TuristaRepository {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");

	private EntityManager em = emf.createEntityManager();

	public boolean addTurista(Turista turista) {

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
		Turista turista = (Turista) this.em.createQuery("select t from Turista t  where IDTurista=" + ID)
				.getResultList().get(0);
		return turista;
	}

	public Turista getTuristaFromEmail(String email) {
		/*
		 * emf e em devono essere istanziati nuovamente dato che se viene effettuato un
		 * update, c'è bisogno di creare una nuova sessione, altrimenti se degli oggetti
		 * sono stati precedentemente caricati dopo l'update nella sessione non abbiamo
		 * oggetti aggiornati. Bisogna quindi creare una nuova sessione e caricarci gli
		 * oggetti aggiornati
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");
		EntityManager em = emf.createEntityManager();
		Turista turista = (Turista) em.createQuery("select t from Turista t  where email LIKE :email")
				.setParameter("email", email).getResultList().get(0);
		return turista;
	}

	public boolean cambiaImmagineTurista(Turista turista, String nomeTurista) {
		try {
			this.em.getTransaction().begin();
			int result = this.em.createQuery("UPDATE Turista SET image = :image WHERE Nome = :nomeTurista")
					.setParameter("image", turista.getImage()).setParameter("nomeTurista", nomeTurista).executeUpdate();
			this.em.getTransaction().commit();

			if (result == 1) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
