package it.univaq.sose.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
		Prenotazione prenotazione = (Prenotazione) this.em
				.createQuery("select p from Prenotazione p where IDPrenotazione=" + ID).getResultList().get(0);
		return prenotazione;
	}

	@SuppressWarnings("unchecked")
	public List<Prenotazione> getPrenotazioniByTurista(int IDTurista) {
		List<Prenotazione> prenotazioni = (List<Prenotazione>) this.em
				.createQuery("select p from Prenotazione p where TuristaPrenotante.IDTurista=" + IDTurista)
				.getResultList();
		return prenotazioni;
	}

	@SuppressWarnings("unchecked")
	public List<Prenotazione> getPrenotazioniByUtenteAttivita(String email) {
		List<Prenotazione> prenotazioni = this.em
				.createQuery("select p from Prenotazione p where Attivita.utenteAttivita.email LIKE :email")
				.setParameter("email", email).getResultList();
		return prenotazioni;
	}
}
