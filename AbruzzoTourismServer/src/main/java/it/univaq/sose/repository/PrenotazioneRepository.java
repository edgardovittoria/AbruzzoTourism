package it.univaq.sose.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.univaq.sose.domain.Attivita;
import it.univaq.sose.domain.Prenotazione;

public class PrenotazioneRepository {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");

	private EntityManager em = emf.createEntityManager();

	public boolean addPrenotazione(Prenotazione prenotazione) {
		try {
			this.em.getTransaction().begin();
			this.em.persist(prenotazione);
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

	public Prenotazione getPrenotazione(int ID) {
		/*
		 * emf e em devono essere istanziati nuovamente dato che se viene effettuato un
		 * update, c'è bisogno di creare una nuova sessione, altrimenti se degli oggetti
		 * sono stati precedentemente caricati dopo l'update, nella sessione non abbiamo
		 * oggetti aggiornati. Bisogna quindi creare una nuova sessione e caricarci gli
		 * oggetti aggiornati
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");
		EntityManager em = emf.createEntityManager();

		Prenotazione prenotazione = (Prenotazione) em
				.createQuery("select p from Prenotazione p where IDPrenotazione=" + ID).getResultList().get(0);
		return prenotazione;
	}

	@SuppressWarnings("unchecked")
	public List<Prenotazione> getPrenotazioniByTurista(int IDTurista) {
		/*
		 * emf e em devono essere istanziati nuovamente dato che se viene effettuato un
		 * update, c'è bisogno di creare una nuova sessione, altrimenti se degli oggetti
		 * sono stati precedentemente caricati dopo l'update, nella sessione non abbiamo
		 * oggetti aggiornati. Bisogna quindi creare una nuova sessione e caricarci gli
		 * oggetti aggiornati
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");
		EntityManager em = emf.createEntityManager();

		List<Prenotazione> prenotazioni = (List<Prenotazione>) em
				.createQuery("select p from Prenotazione p where TuristaPrenotante.IDTurista=" + IDTurista)
				.getResultList();
		return prenotazioni;
	}

	@SuppressWarnings("unchecked")
	public List<Prenotazione> getPrenotazioniByUtenteAttivita(String email) {
		/*
		 * emf e em devono essere istanziati nuovamente dato che se viene effettuato un
		 * update, c'è bisogno di creare una nuova sessione, altrimenti se degli oggetti
		 * sono stati precedentemente caricati dopo l'update, nella sessione non abbiamo
		 * oggetti aggiornati. Bisogna quindi creare una nuova sessione e caricarci gli
		 * oggetti aggiornati
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");
		EntityManager em = emf.createEntityManager();

		List<Prenotazione> prenotazioni = em
				.createQuery("select p from Prenotazione p where Attivita.utenteAttivita.email LIKE :email")
				.setParameter("email", email).getResultList();
		return prenotazioni;
	}
	
	public void deletePrenotazione(int IDPrenotazione) {
		try {
			this.em.getTransaction().begin();
			this.em.createQuery("DELETE Prenotazione WHERE IDPrenotazione = :IDPrenotazione")
					.setParameter("IDPrenotazione", IDPrenotazione)
					.executeUpdate();
			this.em.getTransaction().commit();
		} catch (Exception e) {
			if (this.em.getTransaction() != null) {
				this.em.getTransaction().rollback();
			}
			e.printStackTrace();
		}
	}
	
	public boolean updatePrenotazione(int IDPrenotazione, Prenotazione prenotazione) {

		try {
			this.em.getTransaction().begin();
			int result = this.em.createQuery("UPDATE Prenotazione  SET dataSvolgimentoAttivita = :dataSvolgimentoAttivita, numPartecipanti = :numPartecipanti, costo = :costo WHERE IDPrenotazione = :IDPrenotazione")
					.setParameter("dataSvolgimentoAttivita", prenotazione.getDataSvolgimentoAttivita()).setParameter("numPartecipanti", prenotazione.getNumPartecipanti()).setParameter("costo", prenotazione.getCosto()).setParameter("IDPrenotazione", IDPrenotazione)
					.executeUpdate();
			this.em.getTransaction().commit();

			if (result == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			if (this.em.getTransaction() != null) {
				this.em.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}

	}
}
