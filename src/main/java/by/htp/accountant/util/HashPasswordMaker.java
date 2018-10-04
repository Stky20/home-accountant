package by.htp.accountant.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.exception.PasswordClassUtilException;


final public class HashPasswordMaker implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final HashPasswordMaker instance = new HashPasswordMaker();
	
	private static final String KEY_FILE_NAME = "password.properties";
	private static final String ENCRYPTION_TYPE = "AES";
	
//	private static final String KEY_PATH = "d:/Workspace/home-accountant-version-00/src/main/resources/password.properties";
	private static final Logger logger = LoggerFactory.getLogger("Password.class");                                               		
	
	private HashPasswordMaker() {
		
	}
	
	public static HashPasswordMaker getInstance() {
		return instance;
	}
		
	/**
	 * Method takes String, if String is null or empty this method returns null.
	 * Method creates cipher object then initializing cipher with key from property then
	 * makes hashed bytes then makes string from bytes.
	 * @param String
	 * @return String
	 * @throws PasswordClassUtilException 
	 */
	public String getHashPassword(String passwordFromUser) throws PasswordClassUtilException {
		
		String hashPassword = null;
		
		Cipher cipher = null;
		
		SecretKey key = null;																
		
		byte[] passwordInBytes;
		
		if (passwordFromUser == null) {
			return null;
		} else if(passwordFromUser.trim().isEmpty()) {
			return null;
		}
		
		try {
			key = readKeyFromProperty();																					
		} catch (PasswordClassUtilException e2) {
			logger.info("PasswordClassUtilException while reading key from file", e2);
			throw e2;
		}																						
		
		try {
			cipher = Cipher.getInstance(ENCRYPTION_TYPE);																				
		} catch (NoSuchAlgorithmException e1) {
			logger.info("NoSuchAlgorithmException wrong argument in Copher.getInstance()", e1);									
			throw new PasswordClassUtilException(e1);
		} catch (NoSuchPaddingException e1) {
			logger.info("NoSuchAlgorithmException in Copher.getInstance()", e1);
			throw new PasswordClassUtilException(e1);
		} 																						       
		
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);                                                                         		
		} catch (InvalidKeyException e) {
			logger.info("InvalidKeyException while initializing cipher in method getHashPasssword()", e);
			throw new PasswordClassUtilException(e);
		}		
		
		try {
			passwordInBytes = cipher.doFinal(passwordFromUser.getBytes());                                                      
		} catch (IllegalBlockSizeException e) {
			logger.info("IllegalBlockSizeException while cipher were hashing password", e);
			throw new PasswordClassUtilException(e);
		} catch (BadPaddingException e) {
			logger.info("BadPaddingException while cipher were hashing password", e);
			throw new PasswordClassUtilException(e);
		}		
		
		hashPassword = new String(passwordInBytes);		   													                      		
		
		return hashPassword;
	}	
	
	/**
	 * Method reads SecretKey from property file.	 
	 * @return SecretKey
	 * @throws PasswordClassUtilException 
	 */
	private SecretKey readKeyFromProperty() throws PasswordClassUtilException {		 
		
		SecretKey key = null;		
		String keyFile = getClass().getClassLoader().getResource(KEY_FILE_NAME).getFile();
		
		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(keyFile))) {                         
			
			 key = (SecretKey) objectInputStream.readObject(); 	
			
		} catch (FileNotFoundException e) {			
			throw new PasswordClassUtilException("FileInputStream couldn`t find key file during reading key from propertie.", e);
		} catch (IOException e) {			
			throw new PasswordClassUtilException("IOException while reading key from propertie file in readKeyFromPropertie() method.", e);
		} catch (ClassNotFoundException e) {			
			throw new PasswordClassUtilException("ClassNotFoundException in readKeyFromPropertie() method.", e);
		} 
		
		return key;
	}
	
}
