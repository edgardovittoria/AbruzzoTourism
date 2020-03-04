package it.univaq.sose.main;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

import it.univaq.sose.domain.Turista;
import it.univaq.sose.repository.TuristaRepository;

public class PasswordHandler implements CallbackHandler {
 
	@Override
    public void handle(Callback[] callbacks) throws IOException, 
        UnsupportedCallbackException {
 
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        TuristaRepository turistaRepository = new TuristaRepository();
        Turista turista = new Turista();
        turista = turistaRepository.getTuristaFromEmail(pc.getIdentifier());
 
        if (pc.getIdentifier().equals(turista.getEmail())) {
            // set the password on the callback. This will be compared to the
            // password which was sent from the client.
            pc.setPassword(turista.getPassword());
        }
    }

 
}
