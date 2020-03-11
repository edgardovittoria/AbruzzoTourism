package univaq.aq.it.abruzzotourism.utility;

import android.util.Base64;

import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import univaq.aq.it.abruzzotourism.domain.UserDetails;

public class SecurityHeader {

    private static final String  WSSE_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    private static final String  WSU_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    private static final String  PASSWORD_TEXT = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
    private static final String  BASE64BINARY = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";


    public Element[] generateSecurityHeader(UserDetails userDetails) throws DatatypeConfigurationException, UnsupportedEncodingException, NoSuchAlgorithmException {

        Element headers[] = new Element[1];
        headers[0]= new Element().createElement(null, "wsse:Security");
        headers[0].setPrefix("wsse", WSSE_NAMESPACE);
        headers[0].setPrefix("wsu", WSU_NAMESPACE);
        headers[0].setAttribute(null, "mustUnderstand", "1");

        Element to = new Element().createElement(WSSE_NAMESPACE, "UsernameToken");
        to.setAttribute(WSU_NAMESPACE,"Id", "UsernameToken");

        Element action1 = new Element().createElement(WSSE_NAMESPACE, "Username");
        action1.addChild(Node.TEXT, userDetails.getEmail());
        to.addChild(Node.ELEMENT,action1);



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXX");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        XMLGregorianCalendar createdDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(sdf.format(System.currentTimeMillis()));
        NonceAndPasswordDigest nonceAndPasswordDigest = new NonceAndPasswordDigest();
        byte[] nonce = nonceAndPasswordDigest.generateNonce();

        String nonceBase64Encoded = Base64.encodeToString(nonce,0);

        //MessageDigest md = MessageDigest.getInstance("MD5");
        //String passwordMD5 = Base64.encodeToString(md.digest(userDetails.getPassword().getBytes("UTF-8")),0);
        Element action2 = new Element().createElement(WSSE_NAMESPACE, "Password");
        action2.setAttribute(null, "Type", PASSWORD_TEXT);
        action2.addChild(Node.TEXT, userDetails.getPassword()/*passwordMD5.substring(0,24)*/);
        to.addChild(Node.ELEMENT,action2);


        Element action3 = new Element().createElement(WSSE_NAMESPACE, "Nonce");
        action3.setAttribute(null, "EncodingType", BASE64BINARY);
        action3.addChild(Node.TEXT, nonceBase64Encoded.substring(0,24));
        to.addChild(Node.ELEMENT,action3);

        Element action4 = new Element().createElement(WSU_NAMESPACE, "Created");
        action4.addChild(Node.TEXT, sdf.format(System.currentTimeMillis()));
        to.addChild(Node.ELEMENT,action4);


        headers[0].addChild(Node.ELEMENT, to);

        return headers;
    }
}
