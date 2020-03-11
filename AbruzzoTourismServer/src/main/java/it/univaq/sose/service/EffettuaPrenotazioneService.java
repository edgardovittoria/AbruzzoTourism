package it.univaq.sose.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.interceptor.InInterceptors;

import com.sun.xml.bind.v2.runtime.Name;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.univaq.sose.domain.Attivita;
import it.univaq.sose.domain.Prenotazione;
import it.univaq.sose.domain.TipologiaAttivita;
import it.univaq.sose.domain.Turista;
import it.univaq.sose.domain.UtenteAttivita;

@WebService()
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
@InInterceptors(interceptors = "it.univaq.sose.main.WSSecurityInterceptor")
public interface EffettuaPrenotazioneService {

	@Operation(description = "metodo utilizzato per recuperare un oggetto di tipo Attivita che ha un deterimanto Id", responses = {
			@ApiResponse(description = "Oggetto di tipo Attivita", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))) }) })
	@WebMethod
	@GET
	@Path("/AttivitaById/{ID}")
	public Attivita getAttivitaById(
			@WebParam(name = "ID", targetNamespace = "http://service.sose.univaq.it/") @PathParam("ID") int ID);

	@Operation(description = "Metodo utilizzato per recuperare tutti gli oggetti di tipo Attivita che hanno una determinata tipologia", responses = {
			@ApiResponse(description = "Lista di oggetti di tipo Attivita", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))) }) })
	@WebMethod
	@GET
	@Path("/Attivita/{tipologia}")
	public List<Attivita> getAttivitaByTipologia(
			@WebParam(name = "tipologia", targetNamespace = "http://service.sose.univaq.it/") @PathParam("tipologia") TipologiaAttivita tipologia);

	@Operation(description = "Metodo utilizzato per recuperare tutti gli oggetti di tipo Attivita", responses = {
			@ApiResponse(description = "Lista di oggetti di tipo Attivita", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))) }) })
	@WebMethod
	@GET
	@Path("/Attivita")
	public List<Attivita> getAttivitaHome();

	@Operation(description = "metodo utilizzato per recuperare un oggetto di tipo Prenotazione che ha un deterimanto Id", responses = {
			@ApiResponse(description = "Oggetto di tipo Prenotazione", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))) }) })
	@WebMethod
	@GET
	@Path("/Prenotazione/{ID}")
	public Prenotazione getPrenotazione(
			@WebParam(name = "ID", targetNamespace = "http://service.sose.univaq.it/") @PathParam("ID") int ID);

	@Operation(description = "metodo utilizzato per salvare nel db la prenotazione nel momento in cui viene confermata dall'utente", responses = {
			@ApiResponse(description = "TRUE se il salvataggio è stato effettuato, FALSE altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))) }) })
	@WebMethod
	@POST
	@Path("/confermaPrenotazione")
	public boolean confermaPrenotazione(
			@RequestBody(description = "Ogetto di tipo prenotazione", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))) }) @WebParam(name = "prenotazione", targetNamespace = "http://service.sose.univaq.it/") Prenotazione prenotazione);

	@Operation(description = "metodo utilizzato per recuperare un oggetto di tipo Turista che ha una deterimanta email", responses = {
			@ApiResponse(description = "Oggetto di tipo Turista", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Turista.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Turista.class))) }) })
	@WebMethod
	@GET
	@Path("/TuristaByEmail/{email}")
	public Turista getTuristaByEmail(
			@WebParam(name = "email", targetNamespace = "http://service.sose.univaq.it/") @PathParam("email") String email);

	@Operation(description = "metodo utilizzato per registrare un oggetto di tipo Turista", responses = {
			@ApiResponse(description = "true se la registrazione va a buon fine, false altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Turista.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Turista.class))) }) })
	@WebMethod
	@POST
	@Path("/signinTurista")
	public boolean registrazioneTurista(
			@RequestBody(description = "Ogetto di tipo turista", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Turista.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Turista.class))) }) @WebParam(name = "turista", targetNamespace = "http://service.sose.univaq.it/") Turista turista);

	@Operation(description = "metodo utilizzato per recuperare un immagine relativa ad una determinata attività", responses = {
			@ApiResponse(description = "Immagine codificata in Base64", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = String.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = String.class))) }) })
	@WebMethod
	@GET
	@Path("/getImage/{ID}")
	public String getImageAttivita(@PathParam("ID") int ID);

	@Operation(description = "metodo utilizzato per recuperare un immagine relativa ad una determinata attività", responses = {
			@ApiResponse(description = "Immagine codificata in Base64", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = String.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = String.class))) }) })
	@WebMethod
	@GET
	@Path("/getImageByEmail/{email}")
	public String getImageAttivitaByEmail(@PathParam("email") String email);

	@Operation(description = "metodo utilizzato per recuperare le prenotazioni relative ad un determinato turista", responses = {
			@ApiResponse(description = "Lista di Prenotazioni", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))) }) })
	@GET
	@Path("/getPrenotazioniByIdTurista/{IDTurista}")
	public List<Prenotazione> getPrenotazioniByIdTurista(
			@WebParam(name = "IDTurista", targetNamespace = "http://service.sose.univaq.it/") @PathParam("IDTurista") int IDTurista);

	@Operation(description = "metodo utilizzato per registrare un oggetto di tipo UtenteAttivita", responses = {
			@ApiResponse(description = "true se la registrazione va a buon fine, false altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))) }) })
	@WebMethod
	@POST
	@Path("/signinUtenteAttivita")
	public boolean registrazioneUtenteAttivita(
			@RequestBody(description = "Ogetto di tipo UtenteAttivita", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))) }) @WebParam(name = "utenteAttivita", targetNamespace = "http://service.sose.univaq.it/") UtenteAttivita utenteAttivita);

	@Operation(description = "metodo utilizzato per salvare nel db l'attività che l'utente ha intenzione di inserire tra le attività disponibili", responses = {
			@ApiResponse(description = "TRUE se il salvataggio è stato effettuato, FALSE altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))) }) })
	@WebMethod
	@POST
	@Path("/creaAttivita/{emailUtenteAttivita}")
	public boolean creaAttivita(@RequestBody(description = "Ogetto di tipo attivita", required = true, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))),
			@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))) }) @WebParam(name = "attivita", targetNamespace = "http://service.sose.univaq.it/") Attivita attivita,
			@PathParam("emailUtenteAttivita") String emailUtenteAttivita);

	@Operation(description = "metodo utilizzato per recuperare le prenotazioni relative ad una determinata Attività", responses = {
			@ApiResponse(description = "Lista di Prenotazioni", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))) }) })
	@GET
	@Path("/getPrenotazioniByUtenteAttivita/{email}")
	public List<Prenotazione> getPrenotazioniByUtenteAttivita(
			@WebParam(name = "email", targetNamespace = "http://service.sose.univaq.it/") @PathParam("email") String email);

	@Operation(description = "metodo utilizzato per effettuare il login da parte dell'utenteAttivita", responses = {
			@ApiResponse(description = "TRUE se l'utente è registrato, FALSE altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))) }) })
	@POST
	@Path("/loginUtenteAttivita")
	public boolean login(@RequestBody(description = "Ogetto di tipo UtenteAttivita", required = true, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))),
			@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))) }) @WebParam(name = "utenteAttivita", targetNamespace = "http://service.sose.univaq.it/") UtenteAttivita utenteAttivita);
}
