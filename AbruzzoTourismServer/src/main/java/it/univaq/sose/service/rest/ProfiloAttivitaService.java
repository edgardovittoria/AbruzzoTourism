package it.univaq.sose.service.rest;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.univaq.sose.domain.Attivita;
import it.univaq.sose.domain.Prenotazione;
import it.univaq.sose.domain.UtenteAttivita;

@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
@Path("/ProfiloAttivitaService")
public interface ProfiloAttivitaService {
	
	@Operation(description = "metodo utilizzato per registrare un oggetto di tipo UtenteAttivita", responses = {
			@ApiResponse(description = "true se la registrazione va a buon fine, false altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))) }) })
	@POST
	@Path("/utenteAttivita")
	public boolean registrazioneUtenteAttivita(
			@RequestBody(description = "Ogetto di tipo UtenteAttivita", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))) }) UtenteAttivita utenteAttivita);


	@Operation(description = "metodo utilizzato per salvare nel db l'attività che l'utente ha intenzione di inserire tra le attività disponibili", responses = {
			@ApiResponse(description = "TRUE se il salvataggio è stato effettuato, FALSE altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))) }) })
	@POST
	@Path("/attivita/{emailUtenteAttivita}")
	public boolean creaAttivita(@RequestBody(description = "Ogetto di tipo attivita", required = true, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))),
			@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))) }) Attivita attivita,
			@PathParam("emailUtenteAttivita") String emailUtenteAttivita);
	
	@Operation(description = "metodo utilizzato per recuperare le prenotazioni relative ad una determinata Attività", responses = {
			@ApiResponse(description = "Lista di Prenotazioni", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))) }) })
	@GET
	@Path("/prenotazioni")
	public List<Prenotazione> getPrenotazioniByUtenteAttivita(@QueryParam("emailUtenteAttivita") String email);
	
	@Operation(description = "metodo utilizzato per effettuare il login da parte dell'utenteAttivita", responses = {
			@ApiResponse(description = "TRUE se l'utente è registrato, FALSE altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))) }) })
	@POST
	@Path("/loginUtenteAttivita")
	public boolean login(@RequestBody(description = "Ogetto di tipo UtenteAttivita", required = true, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))),
			@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = UtenteAttivita.class))) }) UtenteAttivita utenteAttivita);


	
	@Operation(description = "metodo utilizzato per recuperare un oggetto di tipo Attivita relativo alla mail dell'utente che ha creato l'attività", responses = {
			@ApiResponse(description = "Oggetto di tipo Attivita", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))) }) })
	@GET
	@Path("/attivita")
	public Attivita getAttivitaByEmail(@QueryParam("email") String email);
	
	@Operation(description = "metodo utilizzato per cambiare l'immagine di un'attività", responses = {
			@ApiResponse(description = "TRUE se l'operazione si svolge correttamente, FALSE altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))) }) })
	@PUT
	@Path("/attivita/{nomeAttivita}")
	public boolean cambiaImmagine(@RequestBody(description = "Ogetto di tipo Attivita", required = true, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))),
			@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Attivita.class))) }) @WebParam(name = "attivita", targetNamespace = "http://service.sose.univaq.it/") Attivita attivita,
			@PathParam("nomeAttivita") String nomeAttivita);
	
	@Operation(description = "metodo utilizzato per salvare nel db la prenotazione nel momento in cui viene confermata dall'utente", responses = {
			@ApiResponse(description = "TRUE se il salvataggio è stato effettuato, FALSE altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))) }) })
	@POST
	@Path("/prenotazioni")
	public boolean aggiungiPrenotazione(
			@RequestBody(description = "Ogetto di tipo prenotazione", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))) }) Prenotazione prenotazione);

	@Operation(description = "metodo utilizzato per modificare una prenotazione", responses = {
			@ApiResponse(responseCode = "204", description = "la risorsa è stata modificata con successo!")})
	@PUT
	@Path("/prenotazioni/{IDPrenotazione}")
	public void modificaPrenotazione(@PathParam("IDPrenotazione") int IDPrenotazione, @RequestBody(description = "Ogetto di tipo Prenotazione", required = true, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))),
			@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))) }) Prenotazione prenotazione);


	@Operation(description = "metodo utilizzato per eliminare una prenotazione", responses = {
			@ApiResponse(responseCode = "204", description = "la risorsa è stata cancellata con successo!")})
	@DELETE
	@Path("/prenotazioni")
	public void eliminaPrenotazione(@QueryParam("IDPrenotazione") int IDPrenotazione);


}
