package by.htp.accountant.bean;

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

import org.apache.log4j.Logger;

import by.htp.accountant.exception.PasswordClassBeanException;


final public class Password implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final Password instance = new Password();
	
	private static final String KEY_FILE_NAME = "password.properties";
	
//	private static final String KEY_PATH = "d:/Workspace/home-accountant-version-00/src/main/resources/password.properties";
	private static final Logger logger = Logger.getLogger("Password.class");                                               		
	
	private Password() {
		
	}
	
	public static Password getInstance() {
		return instance;
	}
		
	/**
	 * Method returns hashPassword, if there are exceptions while 
	 * making hashPassword? returns null. Need to check 
	 * logfile: resources/log/homeaccountant_version_00.log
	 *
	 */
	public String getHashPassword(String passwordFromUser) {
		
		String hashPassword = null;
		
		Cipher cipher = null;
		
		SecretKey key = null;																
		
		byte[] passwordInBytes;
		
		try {
			key = readKeyFromProperty();
		} catch (PasswordClassBeanException e2) {
			logger.info("PasswordClassBeanException while reading key from file", e2);
			return null;
		}																						//read key from file, third method in this class
		
		try {
			cipher = Cipher.getInstance("AES");																				//create cipher object
		} catch (NoSuchAlgorithmException e1) {
			logger.info("NoSuchAlgorithmException wrong argument in Copher.getInstance()", e1);									
			return null;
		} catch (NoSuchPaddingException e1) {
			logger.info("NoSuchAlgorithmException in Copher.getInstance()", e1);
			return null;
		} 																						       
		
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);                                                                         //initializing cipher with our key
		} catch (InvalidKeyException e) {
			logger.info("InvalidKeyException while initializing cipher in method getHashPasssword()", e);
			return null;
		}		
		
		try {
			passwordInBytes = cipher.doFinal(passwordFromUser.getBytes());                                                          // get hash of password in bytes
		} catch (IllegalBlockSizeException e) {
			logger.info("IllegalBlockSizeException while cipher were hashing password", e);
			return null;
		} catch (BadPaddingException e) {
			logger.info("BadPaddingException while cipher were hashing password", e);
			return null;
		}		
		
		hashPassword = new String(passwordInBytes);		   													                      // making string from bytes		
		
		return hashPassword;
	}	
	
	
	private SecretKey readKeyFromProperty() throws PasswordClassBeanException {
		
		ObjectInputStream objectInputStream;
		
		SecretKey key = null;		
		
		try {
			
			objectInputStream = new ObjectInputStream(new FileInputStream(getClass().getClassLoader().getResource(KEY_FILE_NAME).getFile()));  //reading key from file
			
		} catch (FileNotFoundException e) {			
			throw new PasswordClassBeanException("FileInputStream couldn`t find key file during reading key from propertie.", e);
		} catch (IOException e) {			
			throw new PasswordClassBeanException("IOException while reading key from propertie file in readKeyFromPropertie() method.", e);
		} 	
		
		try {		
			
			key = (SecretKey) objectInputStream.readObject(); 																	//reading from file
			
		} catch (ClassNotFoundException e) {			
			throw new PasswordClassBeanException("ClassNotFoundException in readKeyFromPropertie() method.", e);
		} catch (IOException e) {			
			throw new PasswordClassBeanException("IOException while reading key from propertie in readKeyFromPropertie() method.", e);
		}finally {
			try {
				objectInputStream.close();
			} catch (IOException e) {				
				throw new PasswordClassBeanException("IOException while closing ObjectInputStream in readKeyFromPropertie() method.", e);
			} 
		}
		
		return key;
	}
	
}
