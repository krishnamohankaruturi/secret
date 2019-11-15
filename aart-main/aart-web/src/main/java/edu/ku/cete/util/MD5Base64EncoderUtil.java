/**
 * 
 */
package edu.ku.cete.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author neil.howerton
 *
 */
public class MD5Base64EncoderUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Base64EncoderUtil.class);

    /**
     * Concatenates the salt to the baseString, and returns the MD5_base64 ecoding of the string.
     * @param baseString {@link String}
     * @param salt {@link String}
     * @return {@link String}
     */
    public static final String encodeWithSalt(String baseString, String salt) {
        MessageDigest md;
        String ret = new String();
        Random generator = new Random();
        try {
            md = MessageDigest.getInstance("MD5");
            String saltedString = baseString;

            if (salt != null) {
                saltedString = saltedString.concat(salt);
            }

            saltedString = saltedString.concat(String.valueOf(generator.nextInt()));

            md.update(saltedString.getBytes());
            byte[] enc = Base64.encodeBase64(md.digest());

            ret = new String(enc);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Algorithm doesn't exist! Cannot encode with");
        }
        return ret;
    }
    
    /** 
     * @param length int  
     * @return {@link String}
     */
    public static final String getAutoGenerateNumber(int length) {  
        
    	Random random = new Random((new Date()).getTime());
    	char[] values = {'a','b','c','d','e','f','g','h','i','j',
    			'k','l','m','n','o','p','q','r','s','t',
    			'u','v','w','x','y','z','A','B','C','D',
    			'E','F','G','H','I','J',
    			'K','L','M','N','O','P','Q','R','S','T',
    			'U','V','W','X','Y','Z','0','1','2','3',
    			'4','5','6','7','8','9'};

    	String generatedString = "";

    	for (int i=0;i<length;i++) {
    		int idx=random.nextInt(values.length);
    		generatedString += values[idx];
    	}

    	return generatedString;
    }
}
