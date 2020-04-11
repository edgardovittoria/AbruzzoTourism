package it.univaq.sose.service.soap;

import java.util.List;

import javax.jws.WebParam;

import it.univaq.sose.domain.Attivita;
import it.univaq.sose.domain.TipologiaAttivita;
import it.univaq.sose.repository.AttivitaRepository;

public class HomeAndSearchServiceImpl implements HomeAndSearchService{

	private AttivitaRepository attivitaRepository = new AttivitaRepository();
	
	@Override
	public List<Attivita> getAttivitaByTipologia(TipologiaAttivita tipologia) {
		return attivitaRepository.getAttivtaTipologia(tipologia);
	}

	@Override
	public List<Attivita> getAttivitaHome() {
		return attivitaRepository.getAttivitaHome();
	}
}
