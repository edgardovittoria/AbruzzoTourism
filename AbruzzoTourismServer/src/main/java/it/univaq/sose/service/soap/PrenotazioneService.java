package it.univaq.sose.service.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.interceptor.InInterceptors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.univaq.sose.domain.Attivita;
import it.univaq.sose.domain.Prenotazione;
import it.univaq.sose.domain.Turista;

@WebService()
@InInterceptors(interceptors = "it.univaq.sose.main.WSSecurityInterceptor")
public interface PrenotazioneService {

	@WebMethod
	public boolean confermaPrenotazione(
			@WebParam(name = "prenotazione", targetNamespace = "http://soap.service.sose.univaq.it/") Prenotazione prenotazione);

	
	@WebMethod
	public Turista getTuristaByEmail(
			@WebParam(name = "email", targetNamespace = "http://soap.service.sose.univaq.it/") String email);

	
	@WebMethod
	public String getImageAttivita(			
			@WebParam(name = "ID", targetNamespace = "http://soap.service.sose.univaq.it/") int ID);
}
