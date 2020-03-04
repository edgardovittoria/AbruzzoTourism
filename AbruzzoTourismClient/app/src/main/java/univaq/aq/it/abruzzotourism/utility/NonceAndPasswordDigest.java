package univaq.aq.it.abruzzotourism.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.xml.datatype.XMLGregorianCalendar;

import static java.lang.System.currentTimeMillis;
import static java.nio.charset.StandardCharsets.UTF_8;

public class NonceAndPasswordDigest {

    private static final SecureRandom RANDOM;
    private static final int NONCE_SIZE_IN_BYTES = 16;
    private static final String MESSAGE_DIGEST_ALGORITHM_NAME_SHA_1 = "SHA-1";
    private static final String SECURE_RANDOM_ALGORITHM_SHA_1_PRNG = "SHA1PRNG";

    static {
        try {
            RANDOM = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM_SHA_1_PRNG);
            RANDOM.setSeed(currentTimeMillis());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateNonce() {
        byte[] nonceBytes = new byte[NONCE_SIZE_IN_BYTES];
        RANDOM.nextBytes(nonceBytes);
        return nonceBytes;
    }

    public byte[] constructPasswordDigest(byte[] nonceBytes, XMLGregorianCalendar createdDate, String password) {
        try {
            final MessageDigest sha1MessageDigest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM_NAME_SHA_1);
            sha1MessageDigest.update(nonceBytes);
            final String createdDateAsString = createdDate.toString();
            sha1MessageDigest.update(createdDateAsString.getBytes(UTF_8));
            sha1MessageDigest.update(password.getBytes(UTF_8));
            return sha1MessageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
