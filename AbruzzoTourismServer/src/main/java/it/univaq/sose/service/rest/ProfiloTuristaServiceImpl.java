package it.univaq.sose.service.rest;

import java.util.List;

import it.univaq.sose.domain.Prenotazione;
import it.univaq.sose.domain.Turista;
import it.univaq.sose.repository.PrenotazioneRepository;
import it.univaq.sose.repository.TuristaRepository;

public class ProfiloTuristaServiceImpl implements ProfiloTuristaService{

	private TuristaRepository turistaRepository = new TuristaRepository();
	
	private PrenotazioneRepository prenotazioneRepository = new PrenotazioneRepository();
	
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
	public List<Prenotazione> getPrenotazioniByIdTurista(int IDTurista) {
		return prenotazioneRepository.getPrenotazioniByTurista(IDTurista);
	}

	
	
	@Override
	public boolean cambiaImmagineTurista(Turista turista, String nomeTurista) {
		return turistaRepository.cambiaImmagineTurista(turista, nomeTurista);
	}
}
