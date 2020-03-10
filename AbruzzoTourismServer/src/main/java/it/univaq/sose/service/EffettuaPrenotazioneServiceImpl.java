package it.univaq.sose.service;



import java.util.List;

import javax.jws.WebParam;


import it.univaq.sose.domain.Attivita;
import it.univaq.sose.domain.Prenotazione;
import it.univaq.sose.domain.TipologiaAttivita;
import it.univaq.sose.domain.Turista;
import it.univaq.sose.domain.UtenteAttivita;
import it.univaq.sose.repository.AttivitaRepository;
import it.univaq.sose.repository.PrenotazioneRepository;
import it.univaq.sose.repository.TuristaRepository;
import it.univaq.sose.repository.UtenteAttivitaRepository;

public class EffettuaPrenotazioneServiceImpl implements EffettuaPrenotazioneService{
	
	private Turista turista;
		
	private AttivitaRepository attivitaRepository = new AttivitaRepository();
	
	private PrenotazioneRepository prenotazioneRepository = new PrenotazioneRepository();
	
	private TuristaRepository turistaRepository = new TuristaRepository();
	
	private UtenteAttivitaRepository utenteAttivitaRepository = new UtenteAttivitaRepository();
	
	public Turista getTurista() {
		return this.turista;
	}
	
	public void setTurista(Turista turista) {
		this.turista = turista;
	}
	
	@Override
	public Attivita getAttivitaById(int ID) {
		return attivitaRepository.getAttivta(ID);
	}

	@Override
	public List<Attivita> getAttivitaByTipologia(@WebParam(name = "tipologia")TipologiaAttivita tipologia) {
		return attivitaRepository.getAttivtaTipologia(tipologia);
	}

	@Override
	public List<Attivita> getAttivitaHome() {
		return attivitaRepository.getAttivitaHome();
	}

	@Override
	public boolean confermaPrenotazione(Prenotazione prenotazione) {
		prenotazione.setTuristaPrenotante(turistaRepository.getTuristaFromID(prenotazione.getTuristaPrenotante().getIDTurista()));
		if(prenotazioneRepository.addPrenotazione(prenotazione)) {
			return true;
		}
		else {
			return false;
		}
		
		
	}

	@Override
	public Prenotazione getPrenotazione(int ID) {
		return prenotazioneRepository.getPrenotazione(ID);
	}

	@Override
	public Turista getTuristaByEmail(String email) {
		return turistaRepository.getTuristaFromEmail(email);
	}

	@Override
	public boolean registrazioneTurista(Turista turista) {
		try {
			return turistaRepository.addTurista(turista);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getImageAttivita(int ID) {
		return attivitaRepository.getImage(ID);
	}

	@Override
	public List<Prenotazione> getPrenotazioniByIdTurista(int IDTurista) {
		return prenotazioneRepository.getPrenotazioniByTurista(IDTurista);
	}

	@Override
	public boolean registrazioneUtenteAttivita(UtenteAttivita utenteAttivita) {
		return utenteAttivitaRepository.addUtenteAttivita(utenteAttivita);
	}

	@Override
	public boolean creaAttivita(Attivita attivita) {
		try {
			return attivitaRepository.addAttivita(attivita);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Prenotazione> getPrenotazioniByUtenteAttivita(String nomeAttivita) {
		return prenotazioneRepository.getPrenotazioniByUtenteAttivita(nomeAttivita);
	}

	@Override
	public String getImageAttivitaByName(String nomeAttivita) {
		return attivitaRepository.getImageByName(nomeAttivita);
	}
}
	
	