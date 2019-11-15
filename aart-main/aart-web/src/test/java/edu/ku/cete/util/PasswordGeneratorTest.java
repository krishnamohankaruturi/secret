package edu.ku.cete.util;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class PasswordGeneratorTest {
	private ShaPasswordEncoder passwordEncoder;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		passwordEncoder = new ShaPasswordEncoder(512);
	}
	
	@Test
	public void testGeneratePassword() {
		String password = RandomStringUtils.randomAlphabetic(10);
		String salt = RandomStringUtils.randomAlphanumeric(8);
		String encodedPassword = passwordEncoder.encodePassword(password, salt);
		System.out.println("password = "+password);
		System.out.println("salt = "+salt);
		System.out.println("encodedPassword = "+encodedPassword);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
}