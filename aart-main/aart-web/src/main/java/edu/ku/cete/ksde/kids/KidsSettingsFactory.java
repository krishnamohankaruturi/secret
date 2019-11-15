/**
 * 
 */
package edu.ku.cete.ksde.kids;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;


/**
 * @author neil.howerton
 *
 */
public class KidsSettingsFactory {
    /**
     *
     * @param pwd {@link StringIndexOutOfBoundsException}
     * @throws NoSuchAlgorithmException NoSuchalgorithmException
     */
    public static final KidsSettings initialize(String pwd, String username) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String saltedPassword = username.concat(pwd);
        md.update(saltedPassword.getBytes());
        byte[] enc = Base64.encodeBase64(md.digest());

        KidsSettings settings = new KidsSettings();
        settings.setEncryptedPassword(new String(enc));
        settings.setUsername(username);
        return settings;
    }
}
