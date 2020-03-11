package it.univaq.sose.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.univaq.sose.domain.UtenteAttivita;

public class UtenteAttivitaRepository {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");

	private EntityManager em = emf.createEntityManager();

	public boolean addUtenteAttivita(UtenteAttivita utenteAttivita) {
		//turista.setNuovaPrenotazione(null);

		try {
			this.em.getTransaction().begin();
			this.em.persist(utenteAttivita);
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
	
	public UtenteAttivita getUtenteAttivitaFromID(int ID) {
		UtenteAttivita utenteAttivita = (UtenteAttivita) this.em.createQuery("select u from UtenteAttivita u  where IDUtenteAttivita="+ ID).getResultList().get(0);
		return utenteAttivita;
	}
	
	public UtenteAttivita getUtenteAttivitaFromEmail(String email) {
		UtenteAttivita utenteAttivita = (UtenteAttivita) this.em.createQuery("select u from UtenteAttivita u  where email LIKE :email").setParameter("email", email).getResultList().get(0);
		return utenteAttivita;
	}
	
	public boolean loginUtenteAttivita(UtenteAttivita utenteAttivita) {
		try {
			UtenteAttivita utenteAttivitaresult = (UtenteAttivita) this.em.createQuery("select u from UtenteAttivita u  where email LIKE :email").setParameter("email", utenteAttivita.getEmail()).getResultList().get(0);
			if(utenteAttivita.getPassword().equals(utenteAttivitaresult.getPassword())) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
				
	}
	
}
