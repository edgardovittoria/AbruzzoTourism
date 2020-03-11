package it.univaq.sose.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.security.MD5Encoder;

import it.univaq.sose.domain.Attivita;
import it.univaq.sose.domain.Prenotazione;
import it.univaq.sose.domain.TipologiaAttivita;
import it.univaq.sose.domain.Turista;
import it.univaq.sose.domain.UtenteAttivita;
import it.univaq.sose.repository.AttivitaRepository;
import it.univaq.sose.repository.PrenotazioneRepository;
import it.univaq.sose.repository.TuristaRepository;
import it.univaq.sose.repository.UtenteAttivitaRepository;
import sun.security.provider.MD5;

public class TestDB {
	
	public static void main(String[] args) {
		
		PrenotazioneRepository prenotazioneRepository = new PrenotazioneRepository();
		TuristaRepository turistaRepository = new TuristaRepository();
		AttivitaRepository attivitaRepository = new AttivitaRepository();
		UtenteAttivitaRepository utenteAttivitaRepository = new UtenteAttivitaRepository();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AbruzzoTourism");
		EntityManager em = emf.createEntityManager();
		
		
		File file = new File("/home/edgardo/Immagini/Torrentismo.jpg");
		
		byte[] bArray = readFileToByteArray(file);
		String image = Base64.getEncoder().encodeToString(bArray);
		//System.out.println(image);
		UtenteAttivita utenteAttivita = new UtenteAttivita();
		try {
			 utenteAttivita = (UtenteAttivita) em.createQuery("select u from UtenteAttivita u  where email LIKE :email").setParameter("email", "volo1@gmail.com").getResultList().get(0);

		} catch (Exception e) {
			
			utenteAttivita.setEmail("pippo");
		}
		
		/*utenteAttivita.setIDUtenteAttivita(9);
		utenteAttivita.setEmail("volo@gmail.com");
		utenteAttivita.setPassword("fGoYCzaJagqMAnh+6vsOTA==");
		utenteAttivita.setNomeUtenteAttivita("Volo del Falco");*/
		
        System.out.println(utenteAttivita.getEmail());
        
        /*Attivita attivita = new Attivita();
		attivita.setCostoPerPersona(35);
		attivita.setNomeAttivita("Torrentismo");
		attivita.setDescrizione("Torrentismo Descrizione");
		attivita.setNumMaxPartecipanti(15);
		attivita.setImage(image);
		attivita.setTipologia(TipologiaAttivita.Sportiva);
		attivitaRepository.addAttivita(attivita);
		
		/*Attivita attivita1 = Culturale.getInstance();
		attivita1.setCostoPerPersona(12);
		attivita1.setNomeAttivita("Museo Arte");
		attivita1.setNumMaxPartecipanti(15);
		attivita1.setImage(image);
		attivitaRepository.addAttivita(attivita1);*/
		
		/*Attivita attivita2 = FloraFauna.getInstance();
		attivita2.setCostoPerPersona(15);
		attivita2.setNomeAttivita("Visita Camosci");
		attivita2.setNumMaxPartecipanti(10);
		attivita2.setImage(image);
		attivitaRepository.addAttivita(attivita2);*/
		
		/*Attivita attivita3 = Sportiva.getInstance();
		attivita3.setCostoPerPersona(40);
		attivita3.setNomeAttivita("Volo del Falco");
		attivita3.setNumMaxPartecipanti(20);
		attivita3.setImage(image);
		attivitaRepository.addAttivita(attivita3);*/
        
        /*Attivita attivita4 = FloraFauna.getInstance();
		attivita4.setCostoPerPersona(15);
		attivita4.setNomeAttivita("Visita Orso Marsicano");
		attivita4.setNumMaxPartecipanti(10);
		attivita4.setImage(image);
		attivitaRepository.addAttivita(attivita4);*/
        
        //List<Attivita> attivita5 = attivitaRepository.getAttivtaTipologia(TipologiaAttivita.Culturale);
        //System.out.println(attivita5.get(0).getDescrizione());
        /*Attivita attivita5 = new Attivita();
        
		attivita5.setCostoPerPersona(12);
		attivita5.setNomeAttivita("Museo Geopaleontologico");
		attivita5.setNumMaxPartecipanti(15);
		attivita5.setImage(image);
		attivita5.setDescrizione("Museon jdiubdbia");
		attivita5.setTipologia(TipologiaAttivita.Culturale);
		
		attivitaRepository.addAttivita(attivita5);*/
		
		/*prenotazione.setAttivita(attivita5);
		prenotazione.setConfermata(true);
		prenotazione.setCosto(34);
		prenotazione.setNumPartecipanti(12);
		prenotazione.setPagata(true);
		prenotazione.setTuristaPrenotante(null);
		prenotazioneRepository.addPrenotazione(prenotazione);*/
        
        /*TuristaRepository turistaRepository = new TuristaRepository();
        Turista turista = new Turista();
        turista.setEmail("eddy3");
        try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			String passwordMD5 = Base64.getEncoder().encodeToString(md.digest("password".getBytes()));
			turista.setPassword(passwordMD5);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		//turistaRepository.addTurista(turista);
        //System.out.println(turistaRepository.getTuristaFromEmail("eddy3").getEmail());
        
        
	
	}
	
	
	/**
     * This method uses java.io.FileInputStream to read
     * file content into a byte array
     * @param file
     * @return
     */
	private static byte[] readFileToByteArray(File file){
        FileInputStream fis = null;
        // Creating a byte array using the length of the file
        // file.length returns long which is cast to int
        byte[] bArray = new byte[(int) file.length()];
        try{
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();        
            
        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return bArray;
    }
}
		
		
		
	

