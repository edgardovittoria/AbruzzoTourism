package it.univaq.sose.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.univaq.sose.domain.Attivita;
import it.univaq.sose.domain.TipologiaAttivita;

public class AttivitaRepository {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");

	private EntityManager em = emf.createEntityManager();

	public boolean addAttivita(Attivita attivita) {

		try {
			this.em.getTransaction().begin();
			this.em.persist(attivita);
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

	public Attivita getAttivta(int ID) {
		Attivita attivita = (Attivita) this.em.createQuery("select a from Attivita a where IDAttività=" + ID)
				.getResultList().get(0);
		return attivita;
	}

	@SuppressWarnings("unchecked")
	public List<Attivita> getAttivtaTipologia(TipologiaAttivita tipologia) {
		/*
		 * emf e em devono essere istanziati nuovamente dato che se viene effettuato un
		 * update, c'è bisogno di creare una nuova sessione, altrimenti se degli oggetti
		 * sono stati precedentemente caricati dopo l'update nella sessione non abbiamo
		 * oggetti aggiornati. Bisogna quindi creare una nuova sessione e caricarci gli
		 * oggetti aggiornati
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");
		EntityManager em = emf.createEntityManager();
		List<Attivita> attivita = em.createQuery("select a from Attivita a where tipologia LIKE :tipologia")
				.setParameter("tipologia", tipologia).getResultList();
		return attivita;
	}

	public Attivita getAttivitaNome(String nome) {
		Attivita attivita = (Attivita) this.em.createQuery("select a from Attivita a where nomeAttivita LIKE :nome")
				.setParameter("nome", nome).getResultList().get(0);
		return attivita;
	}

	public Attivita getAttivitaEmail(String email) {
		/*
		 * emf e em devono essere istanziati nuovamente dato che se viene effettuato un
		 * update, c'è bisogno di creare una nuova sessione, altrimenti se degli oggetti
		 * sono stati precedentemente caricati dopo l'update nella sessione non abbiamo
		 * oggetti aggiornati. Bisogna quindi creare una nuova sessione e caricarci gli
		 * oggetti aggiornati
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");
		EntityManager em = emf.createEntityManager();
		Attivita attivita = (Attivita) em.createQuery("select a from Attivita a where utenteAttivita.email LIKE :email")
				.setParameter("email", email).getResultList().get(0);
		return attivita;
	}

	@SuppressWarnings("unchecked")
	public List<Attivita> getAttivitaHome() {
		/*
		 * emf e em devono essere istanziati nuovamente dato che se viene effettuato un
		 * update, c'è bisogno di creare una nuova sessione, altrimenti se degli oggetti
		 * sono stati precedentemente caricati dopo l'update nella sessione non abbiamo
		 * oggetti aggiornati. Bisogna quindi creare una nuova sessione e caricarci gli
		 * oggetti aggiornati
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");
		EntityManager em = emf.createEntityManager();
		List<Attivita> attivita = em.createQuery("SELECT a FROM Attivita a").setMaxResults(100).getResultList();
		return attivita;
	}

	public String getImage(int ID) {
		String imageString = (String) this.em.createQuery("SELECT a.image FROM Attivita a WHERE IDAttività=" + ID)
				.getResultList().get(0);
		return imageString;
	}

	public String getImageByEmail(String email) {
		System.out.print(email);
		String imageString = (String) this.em
				.createQuery("SELECT a.image FROM Attivita a WHERE utenteAttivita.email LIKE :email")
				.setParameter("email", email).getResultList().get(0);
		return imageString;
	}

	public boolean setImageAttivita(Attivita attivita, String nomeAttivita) {

		try {
			this.em.getTransaction().begin();
			int result = this.em.createQuery("UPDATE Attivita  SET image = :image WHERE nomeAttivita = :nomeAttivita")
					.setParameter("image", attivita.getImage()).setParameter("nomeAttivita", nomeAttivita)
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
