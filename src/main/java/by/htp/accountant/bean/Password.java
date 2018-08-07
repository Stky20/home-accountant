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


import by.htp.accountant.exception.PasswordClassBeanException;

final public class Password implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final String KEY_PATH = "password.properties";
	
//	private static final Logger logger = Logger.getLogger("Password.class");                                               //Убрать логгеры, логироваться будет в контроллере
	
	
			
	public static String getHashPassword(String passwordFromUser) throws PasswordClassBeanException {
		
		String hashPassword = "";
		
		Cipher cipher = null;
		
		try {
			cipher = Cipher.getInstance("AES");																				// объект шифровальщик
		} catch (NoSuchAlgorithmException e1) {
//			logger.info("NoSuchAlgorithmException wrong argument in Copher.getInstance()");									
			throw new PasswordClassBeanException("NoSuchAlgorithmException wrong argument in Copher.getInstance()", e1);
		} catch (NoSuchPaddingException e1) {
//			logger.info("NoSuchAlgorithmException in Copher.getInstance()");
			throw new PasswordClassBeanException("NoSuchPaddingException in Copher.getInstance()", e1);
		}  														
		
		SecretKey key = null;
		
		key = readKeyFromProperty();																				       //читаем из файла, третий метод класса Password      		
		
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);                                                                         //инитим шифровальщика для кодирования с нашим ключем
		} catch (InvalidKeyException e) {
//			logger.info("IOException while reading password from propertie in method readKeyFromPropertie()");
			throw new PasswordClassBeanException("IOException while reading password from propertie", e);
		}
		
		byte[] bytes1;
		try {
			bytes1 = cipher.doFinal(passwordFromUser.getBytes());                                                          // получаем получаем хэш в байтах
		} catch (IllegalBlockSizeException e) {
//			logger.info("IllegalBlockSizeException while cipher were hashing password");
			throw new PasswordClassBeanException("IllegalBlockSizeException while cipher were hashing password", e);
		} catch (BadPaddingException e) {
//			logger.info("BadPaddingException while cipher were hashing password");
			throw new PasswordClassBeanException("BadPaddingException while cipher were hashing password", e);
		}
		
				
		for(byte b: bytes1) {
			char ch = (char) b;
			hashPassword = hashPassword + ch;   													                      // наш хэштрованный пароль из байтов в стринге
		}
		
		return hashPassword;
	}
	
	
	public static boolean checkPassword(String hashPasswordFromUser, String hashPasswordFromDB) {
		
		if(hashPasswordFromUser.equals(hashPasswordFromDB)) return true;
		
		return false;
	}
	
	
	private static SecretKey readKeyFromProperty() throws PasswordClassBeanException {
		
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(KEY_PATH));
		} catch (FileNotFoundException e) {
//			logger.info("FileInputStream couldn`t find key file.");
			throw new PasswordClassBeanException("FileInputStream couldn`t find key file during reading key from propertie.", e);
		} catch (IOException e) {
//			logger.info("IOException while reading key from propertie file in readKeyFromPropertie() method.");
			throw new PasswordClassBeanException("IOException while reading key from propertie file in readKeyFromPropertie() method.", e);
		} 
		SecretKey key;
		try {
			key = (SecretKey) ois.readObject();
		} catch (ClassNotFoundException e) {
//			logger.info("ClassNotFoundException in readKeyFromPropertie() method.");
			throw new PasswordClassBeanException("ClassNotFoundException in readKeyFromPropertie() method.", e);
		} catch (IOException e) {
//			logger.info("IOException while reading key from propertie in readKeyFromPropertie() method.");
			throw new PasswordClassBeanException("IOException while reading key from propertie in readKeyFromPropertie() method.", e);
		}finally {                                              																							 //читаем из файла
			try {
				ois.close();
			} catch (IOException e) {
//				logger.info("IOException while closing ObjectInputStream in readKeyFromPropertie() method.");
				throw new PasswordClassBeanException("IOException while closing ObjectInputStream in readKeyFromPropertie() method.", e);
			} 
		}
		
		return key;
	}
	
}
