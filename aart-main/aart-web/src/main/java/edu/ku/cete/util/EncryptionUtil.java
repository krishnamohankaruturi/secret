package edu.ku.cete.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.ku.cete.service.ServiceException;

/**
* This class is utility for password. It encrypts the password using "MD5"
* encoder
* 
*/
public class EncryptionUtil
{
   private static final Logger logger = LoggerFactory
           .getLogger(EncryptionUtil.class);

   // Create encrypter/decrypter class
   private static Encrypter desEncrypter = new Encrypter("KEY");  

   /**
    * Encrypts the given string returns encrypted string
    * 
    * @param inputString
    * @return
    */
   public static String encrypt(String strToEncrypt)
   {
       return desEncrypter.encrypt(strToEncrypt);
   }

   public static String decrypt(String strToDecrypt)
           throws ServiceException
   {
       logger.debug("Decoding ::" + strToDecrypt);
       String val = desEncrypter.decrypt(strToDecrypt);
       if (val == null)
       {
           throw new ServiceException("Invalid Encrypted String");
       }
       return val;
   }

   /**
    * This method encrypts the given string and return encodes string using
    * UTF-8.
    * 
    * @param strToEncrypt
    * @return
    */
   public static String encryptAndEncode(String strToEncrypt)
   {
       String val = null;
       try
       {
           val = URLEncoder.encode(EncryptionUtil.encrypt(strToEncrypt),
                   "UTF-8");
       }
       catch (UnsupportedEncodingException e)
       {
           logger.error("Error occured while eccoding the string: "
                   + e.getMessage());
       }
       return val;
   }
}
