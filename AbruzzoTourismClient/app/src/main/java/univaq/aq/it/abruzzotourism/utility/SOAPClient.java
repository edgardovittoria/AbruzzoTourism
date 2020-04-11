package univaq.aq.it.abruzzotourism.utility;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import univaq.aq.it.abruzzotourism.domain.Prenotazione;
import univaq.aq.it.abruzzotourism.domain.UserDetails;

import static android.content.ContentValues.TAG;


public class SOAPClient {

    private String SOAP_ACTION = "";
    private String NAMESPACE = "http://soap.service.sose.univaq.it/";
    private String WSDL_URL_HOMEANDSEARCH_SERVICE = "http://10.0.2.2:8080/AbruzzoTourism/soap/HomeAndSearchService";
    private String WSDL_URL_PRENOTAZIONE_SERVICE = "http://10.0.2.2:8080/AbruzzoTourism/soap/PrenotazioneService";
    private SoapObject result;

    public SoapObject getAttivitaByTipologia(Object tipologia, Object userDetails) {
        
        try {

            SoapObject Request = new SoapObject(this.NAMESPACE, "getAttivitaByTipologia");
            Request.addProperty("tipologia", tipologia);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = false;
            // add securityheader to envelope
            SecurityHeader securityHeader = new SecurityHeader();
            soapEnvelope.headerOut = securityHeader.generateSecurityHeader((UserDetails) userDetails);
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(this.WSDL_URL_HOMEANDSEARCH_SERVICE);

            transport.call(this.SOAP_ACTION, soapEnvelope);

            this.result = (SoapObject)soapEnvelope.bodyIn;

        } catch (Exception ex) {
            Log.e(TAG, "catch: " + ex.getMessage());
        }

        return this.result;
    }


    public SoapObject getAttivitaHome(UserDetails userDetails) {

        try {

            SoapObject Request = new SoapObject(this.NAMESPACE, "getAttivitaHome");

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = false;
            soapEnvelope.implicitTypes = true;
            // add securityheader to envelope
            SecurityHeader securityHeader = new SecurityHeader();
            soapEnvelope.headerOut = securityHeader.generateSecurityHeader(userDetails);


            soapEnvelope.setOutputSoapObject(Request);


            HttpTransportSE transport = new HttpTransportSE(this.WSDL_URL_HOMEANDSEARCH_SERVICE);

            transport.call(this.SOAP_ACTION, soapEnvelope);

            this.result = (SoapObject)soapEnvelope.bodyIn;



        } catch (Exception ex) {
            Log.e(TAG, "catch: " + ex.getMessage());

        }


        return this.result;
    }

    public SoapObject confermaPrenotazione(Object prenotazione, Object userDetails) {

        try {

            Prenotazione p = (Prenotazione) prenotazione;

            SoapObject Request = new SoapObject(this.NAMESPACE, "confermaPrenotazione");
            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setName("prenotazione");
            propertyInfo.setValue(p);
            propertyInfo.setType(p.getClass());
            Request.addProperty(propertyInfo);


            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.encodingStyle = SoapSerializationEnvelope.XSD;
            MarshallFloat mf = new MarshallFloat();
            mf.register(soapEnvelope);

            // add securityheader to envelope
            SecurityHeader securityHeader = new SecurityHeader();
            soapEnvelope.headerOut = securityHeader.generateSecurityHeader((UserDetails) userDetails);
            soapEnvelope.setOutputSoapObject(Request);


            soapEnvelope.addMapping(NAMESPACE, "Prenotazione", new Prenotazione().getClass());



            HttpTransportSE transport = new HttpTransportSE(this.WSDL_URL_PRENOTAZIONE_SERVICE);

            transport.call(this.SOAP_ACTION, soapEnvelope);

            this.result = (SoapObject)soapEnvelope.bodyIn;


        } catch (Exception ex) {
            Log.e(TAG, "catch: " + ex.getMessage());
        }
        return this.result;
    }

    public SoapObject getImageAttivita(Object ID, Object userDetails) {

        try {


            SoapObject Request = new SoapObject(this.NAMESPACE, "getImageAttivita");
            Request.addProperty("ID", ID);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = false;
            // add securityheader to envelope
            SecurityHeader securityHeader = new SecurityHeader();
            soapEnvelope.headerOut = securityHeader.generateSecurityHeader((UserDetails) userDetails);
            soapEnvelope.setOutputSoapObject(Request);


            HttpTransportSE transport = new HttpTransportSE(this.WSDL_URL_PRENOTAZIONE_SERVICE);

            transport.call(this.SOAP_ACTION, soapEnvelope);

            this.result = (SoapObject)soapEnvelope.bodyIn;



        } catch (Exception ex) {
            Log.e(TAG, "catch: " + ex.getMessage());

        }


        return this.result;
    }

    public SoapObject getTuristaByEmail(UserDetails userDetails) {

        try {


            SoapObject Request = new SoapObject(this.NAMESPACE, "getTuristaByEmail");
            Request.addProperty("email", userDetails.getEmail());

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = false;
            // add securityheader to envelope
            SecurityHeader securityHeader = new SecurityHeader();
            soapEnvelope.headerOut = securityHeader.generateSecurityHeader((UserDetails) userDetails);
            soapEnvelope.setOutputSoapObject(Request);


            HttpTransportSE transport = new HttpTransportSE(this.WSDL_URL_PRENOTAZIONE_SERVICE);

            transport.call(this.SOAP_ACTION, soapEnvelope);

            this.result = (SoapObject)soapEnvelope.bodyIn;



        } catch (Exception ex) {
            Log.e(TAG, "catch: " + ex.getMessage());

        }


        return this.result;
    }
}
