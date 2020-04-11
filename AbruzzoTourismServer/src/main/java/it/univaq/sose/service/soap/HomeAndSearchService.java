package it.univaq.sose.service.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.interceptor.InInterceptors;

import it.univaq.sose.domain.Attivita;
import it.univaq.sose.domain.TipologiaAttivita;

@WebService()
@InInterceptors(interceptors = "it.univaq.sose.main.WSSecurityInterceptor")
public interface HomeAndSearchService {

	@WebMethod
	public List<Attivita> getAttivitaByTipologia(
			@WebParam(name = "tipologia", targetNamespace = "http://soap.service.sose.univaq.it/") TipologiaAttivita tipologia);

	@WebMethod
	public List<Attivita> getAttivitaHome();
}
