package edu.ku.cete.service.impl.api;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum APIHash {
	
	MD5("MD5"),
	SHA1("SHA1"),
	SHA256("SHA-256"),
	SHA512("SHA-512");
	
	private static Logger logger = LoggerFactory.getLogger(APIHash.class);
	
	private String name;

	APIHash(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	protected static String toHex(byte[] bytes) {
		if (bytes == null) return null;
		return DatatypeConverter.printHexBinary(bytes);
	}
	
	/**
	 * 
	 * @param text the text to convert
	 * @param encoding a valid encoding, such as "UTF-8" (optional)
	 * @return the bytes from the digest
	 */
	public byte[] checksum(String text, String encoding) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(getName());
			if (encoding == null) {
				return digest.digest(text.getBytes());
			} else {
				return digest.digest(text.getBytes(encoding));
			}
		} catch (Exception e) {
			logger.error("Error hashing: ", e);
		}
		return null;
	}
	
	/**
	 * 
	 * @param text the text to convert
	 * @param encoding a valid encoding, such as "UTF-8" (optional)
	 * @return the hex values for the hash
	 */
	public String checksumToHex(String text, String encoding) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(getName());
			if (encoding == null) {
				return toHex(digest.digest(text.getBytes()));
			} else {
				return toHex(digest.digest(text.getBytes(encoding)));
			}
		} catch (Exception e) {
			logger.error("Error hashing: ", e);
		}
		return null;
	}
}
