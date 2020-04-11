package it.univaq.sose.service.rest;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
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
import it.univaq.sose.domain.Prenotazione;
import it.univaq.sose.domain.Turista;


@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
@Path("/ProfiloTuristaService")
public interface ProfiloTuristaService {

	@Operation(description = "metodo utilizzato per recuperare un oggetto di tipo Turista che ha una deterimanta email", responses = {
			@ApiResponse(description = "Oggetto di tipo Turista", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Turista.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Turista.class))) }) })
	@GET
	@Path("/turisti")
	public Turista getTuristaByEmail(@QueryParam("email") String email);

	@Operation(description = "metodo utilizzato per registrare un oggetto di tipo Turista", responses = {
			@ApiResponse(description = "true se la registrazione va a buon fine, false altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Turista.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Turista.class))) }) })
	@POST
	@Path("/turisti")
	public boolean registrazioneTurista(
			@RequestBody(description = "Ogetto di tipo turista", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Turista.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Turista.class))) }) @WebParam(name = "turista", targetNamespace = "http://service.sose.univaq.it/") Turista turista);

	@Operation(description = "metodo utilizzato per recuperare le prenotazioni relative ad un determinato turista", responses = {
			@ApiResponse(description = "Lista di Prenotazioni", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Prenotazione.class))) }) })
	@GET
	@Path("/prenotazioni")
	public List<Prenotazione> getPrenotazioniByIdTurista(@QueryParam("IDTurista") int IDTurista);

	@Operation(description = "metodo utilizzato per cambiare l'immagine profilo di un Turista", responses = {
			@ApiResponse(description = "TRUE se l'operazione si svolge correttamente, FALSE altrimenti", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Boolean.class))) }) })
	@PUT
	@Path("/turisti/{nomeTurista}")
	public boolean cambiaImmagineTurista(
			@RequestBody(description = "Ogetto di tipo Turista", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Turista.class))),
					@Content(mediaType = MediaType.APPLICATION_XML, array = @ArraySchema(schema = @Schema(implementation = Turista.class))) }) Turista turista,
			@PathParam("nomeTurista") String nomeTurista);

}
