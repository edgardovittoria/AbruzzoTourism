package it.univaq.sose.service.rest;

import java.util.List;

import it.univaq.sose.domain.Attivita;
import it.univaq.sose.domain.Prenotazione;
import it.univaq.sose.domain.UtenteAttivita;
import it.univaq.sose.repository.AttivitaRepository;
import it.univaq.sose.repository.PrenotazioneRepository;
import it.univaq.sose.repository.TuristaRepository;
import it.univaq.sose.repository.UtenteAttivitaRepository;

public class ProfiloAttivitaServiceImpl implements ProfiloAttivitaService{
	
	private PrenotazioneRepository prenotazioneRepository = new PrenotazioneRepository();
	
	private AttivitaRepository attivitaRepository = new AttivitaRepository();
	
	private TuristaRepository turistaRepository = new TuristaRepository();
	
	private UtenteAttivitaRepository utenteAttivitaRepository = new UtenteAttivitaRepository();

	@Override
	public boolean creaAttivita(Attivita attivita, String emailUtenteAttivita) {
		try {
			UtenteAttivita utenteAttivita = utenteAttivitaRepository.getUtenteAttivitaFromEmail(emailUtenteAttivita);
			attivita.setUtenteAttivita(utenteAttivita);
			return attivitaRepository.addAttivita(attivita);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Prenotazione> getPrenotazioniByUtenteAttivita(String email) {
		return prenotazioneRepository.getPrenotazioniByUtenteAttivita(email);
	}

	@Override
	public boolean registrazioneUtenteAttivita(UtenteAttivita utenteAttivita) {
		return utenteAttivitaRepository.addUtenteAttivita(utenteAttivita);
	}

	@Override
	public boolean login(UtenteAttivita utenteAttivita) {
		return utenteAttivitaRepository.loginUtenteAttivita(utenteAttivita);
	}

	@Override
	public Attivita getAttivitaByEmail(String email) {
		return attivitaRepository.getAttivitaEmail(email);
	}

	@Override
	public boolean cambiaImmagine(Attivita attivita, String nomeAttivita) {
		return attivitaRepository.setImageAttivita(attivita, nomeAttivita);
	}

	@Override
	public boolean aggiungiPrenotazione(Prenotazione prenotazione) {
		return prenotazioneRepository.addPrenotazione(prenotazione);
	}
	
	@Override
	public void modificaPrenotazione(int IDPrenotazione, Prenotazione prenotazione) {
		System.out.println(IDPrenotazione);
		System.out.println(prenotazione.getNumPartecipanti());

		prenotazioneRepository.updatePrenotazione(IDPrenotazione, prenotazione);
	}
	
	@Override
	public void eliminaPrenotazione(int IDPrenotazione) {
		prenotazioneRepository.deletePrenotazione(IDPrenotazione);
	}
}
