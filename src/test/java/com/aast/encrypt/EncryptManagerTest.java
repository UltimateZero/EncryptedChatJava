package com.aast.encrypt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by UltimateZero on 5/6/2017.
 */
public class EncryptManagerTest {
	@Test
	public void encryptAES() throws Exception {
		String enc = EncryptManager.encryptAES("test", "data");
		assertEquals("uYXnFleOfcAa9U7tlJP23g==", enc);
	}

	@Test
	public void encryptAESLongKey() throws Exception {
		String enc = EncryptManager.encryptAES("testttttttttttttttttttttttttttttttttttttttttttttttttttttttt", "data");
		assertEquals("BfVEd/d4aTjF1DuuEPvVUw==", enc);
	}

	@Test
	public void encryptAESArabicData() throws Exception {
		String enc = EncryptManager.encryptAES("test", "عربي");
		assertEquals("9zMEKrT1Vgul6MCo0m3pdQ==", enc);
	}

	@Test
	public void encryptAESArabicKey() throws Exception {
		String enc = EncryptManager.encryptAES("عربي", "عربي");
		assertEquals("iTFkgbHeXLIOIPdLyOYpyA==", enc);
	}


	@Test
	public void decryptAES() throws Exception {
		String dec = EncryptManager.decryptAES("test", "uYXnFleOfcAa9U7tlJP23g==");
		assertEquals("data", dec);
	}

	@Test
	public void decryptAESLongKey() throws Exception {
		String dec = EncryptManager.decryptAES("testttttttttttttttttttttttttttttttttttttttttttttttttttttttt", "BfVEd/d4aTjF1DuuEPvVUw==");
		assertEquals("data", dec);
	}

	@Test
	public void decryptAESArabicData() throws Exception {
		String dec = EncryptManager.decryptAES("test", "9zMEKrT1Vgul6MCo0m3pdQ==");
		assertEquals("عربي", dec);
	}

	@Test
	public void decryptAESArabicKey() throws Exception {
		String dec = EncryptManager.decryptAES("عربي", "iTFkgbHeXLIOIPdLyOYpyA==");
		assertEquals("عربي", dec);
	}

	@Test
	public void setKey() throws Exception {
		EncryptManager.setKey("123");
		assertEquals("123", EncryptManager.key);
	}

}