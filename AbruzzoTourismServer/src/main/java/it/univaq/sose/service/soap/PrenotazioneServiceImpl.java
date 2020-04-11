package it.univaq.sose.service.soap;

import it.univaq.sose.domain.Prenotazione;
import it.univaq.sose.domain.Turista;
import it.univaq.sose.repository.AttivitaRepository;
import it.univaq.sose.repository.PrenotazioneRepository;
import it.univaq.sose.repository.TuristaRepository;

public class PrenotazioneServiceImpl implements PrenotazioneService{
	
	private TuristaRepository turistaRepository = new TuristaRepository();
	
	private PrenotazioneRepository prenotazioneRepository = new PrenotazioneRepository();

	private AttivitaRepository attivitaRepository = new AttivitaRepository();
	
	@Override
	public boolean confermaPrenotazione(Prenotazione prenotazione) {
		/*if(prenotazione.getTuristaPrenotante() != null) {
			prenotazione.setTuristaPrenotante(
					turistaRepository.getTuristaFromID(prenotazione.getTuristaPrenotante().getIDTurista()));
		}
		else {
			prenotazione.setTuristaPrenotante(null);
		}*/
		
		if (prenotazioneRepository.addPrenotazione(prenotazione)) {
			return true;
		} else {
			return false;
		}

	}
	
	@Override
	public Turista getTuristaByEmail(String email) {
		return turistaRepository.getTuristaFromEmail(email);
	}
	
	@Override
	public String getImageAttivita(int ID) {
		return attivitaRepository.getImage(ID);
	}
}
