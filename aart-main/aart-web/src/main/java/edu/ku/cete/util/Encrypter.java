package edu.ku.cete.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encrypter
{
    private static final Logger logger = LoggerFactory
            .getLogger(Encrypter.class);

    Cipher ecipher;
    Cipher dcipher;

    /**
     * Constructor used to create this object. Responsible for setting
     * and initializing this object's encrypter and decrypter Chipher instances
     * given a Secret Key and algorithm.
     *
     * @param key
     *            Secret Key used to initialize both the encrypter and
     *            decrypter instances.
     * @param algorithm
     *            Which algorithm to use for creating the encrypter and
     *            decrypter instances.
     */
    Encrypter(SecretKey key, String algorithm)
    {
        try
        {
            ecipher = Cipher.getInstance(algorithm);
            dcipher = Cipher.getInstance(algorithm);
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        }
        catch (NoSuchPaddingException e)
        {
            logger.error("EXCEPTION: NoSuchPaddingException");
        }
        catch (NoSuchAlgorithmException e)
        {
           logger.error("EXCEPTION: NoSuchAlgorithmException");
        }
        catch (InvalidKeyException e)
        {
            logger.error("EXCEPTION: InvalidKeyException");
        }
    }

    /**
     * Constructor used to create this object. Responsible for setting
     * and initializing this object's encrypter and decrypter Chipher instances
     * given a Pass Phrase and algorithm.
     *
     * @param passPhrase
     *            Pass Phrase used to initialize both the encrypter and
     *            decrypter instances.
     */
    Encrypter(String passPhrase)
    {

        // 8-bytes Salt
        byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
                (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 };

        // Iteration count
        int iterationCount = 19;

        try
        {

            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,
                    iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
                    .generateSecret(keySpec);

            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameters to the cipthers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
                    iterationCount);

            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        }
        catch (InvalidAlgorithmParameterException e)
        {
            logger.error("EXCEPTION: InvalidAlgorithmParameterException");
        }
        catch (InvalidKeySpecException e)
        {
            logger.error("EXCEPTION: InvalidKeySpecException");
        }
        catch (NoSuchPaddingException e)
        {
            logger.error("EXCEPTION: NoSuchPaddingException");
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error("EXCEPTION: NoSuchAlgorithmException");
        }
        catch (InvalidKeyException e)
        {
            logger.error("EXCEPTION: InvalidKeyException");
        }
    }

    /**
     * Takes a single String as an argument and returns an Encrypted version
     * of that String.
     *
     * @param str
     *            String to be encrypted
     * @return <code>String</code> Encrypted version of the provided String
     */
    public String encrypt(String str)
    {
        try
        {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        }
        catch (BadPaddingException e)
        {}
        catch (IllegalBlockSizeException e)
        {}
        catch (UnsupportedEncodingException e)
        {}
        catch (IOException e)
        {}
        return null;
    }

    /**
     * Takes a encrypted String as an argument, decrypts and returns the
     * decrypted String.
     *
     * @param str
     *            Encrypted String to be decrypted
     * @return <code>String</code> Decrypted version of the provided String
     */
    public String decrypt(String str)
    {
        try
        {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        }
        catch (BadPaddingException e)
        {}
        catch (IllegalBlockSizeException e)
        {}
        catch (UnsupportedEncodingException e)
        {}
        catch (IOException e)
        {}
        return null;
    }
}