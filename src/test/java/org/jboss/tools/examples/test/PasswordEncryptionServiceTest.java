package org.jboss.tools.examples.test;

import static org.junit.Assert.*;

import org.jboss.tools.examples.service.PasswordEncryptionService;
import org.junit.Test;

public class PasswordEncryptionServiceTest {

	@Test
	public void testPasswordGeneration() {
		try {
			byte[] encPwd = PasswordEncryptionService.getEncryptedPassword("password");
			assertTrue(encPwd != null);
			assertTrue(encPwd.length == 28);
		}
		catch(Exception e) {
			fail("Should not have trown exception");
		}
	}
	
	@Test
	public void testAuthentication() {
		try {
			byte[] encPwd1 = PasswordEncryptionService.getEncryptedPassword("password");
			assertTrue(PasswordEncryptionService.authenticate("password", encPwd1));
		}
		catch(Exception e) {
			fail("Should not have trown exception");
		}
	}
	
}
