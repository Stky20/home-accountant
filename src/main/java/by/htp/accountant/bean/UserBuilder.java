package by.htp.accountant.bean;

import by.htp.accountant.exception.PasswordClassBeanException;

public class UserBuilder {
	
	private int id;
	private String nickName;
	private String hashPassword;
	private String name;
	private String surname;
	private String eMail;
	private int role;
	
	public UserBuilder buildId(int id) {
		this.id = id;
		return this;		
	}
	
	public UserBuilder buildNickName(String nickName) {
		this.nickName = nickName;
		return this;		
	}
	
	public UserBuilder buildExistingHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
		return this;		
	}
	
	public UserBuilder buildHashFromPassword(String password) throws PasswordClassBeanException {
		
		this.hashPassword = Password.getInstance().getHashPassword(password);
		
		return this;		
	}
	
	public UserBuilder buildName(String name) {
		this.name = name;
		return this;		
	}
	
	public UserBuilder buildSurname(String surname) {
		this.surname = surname;
		return this;		
	}
	
	public UserBuilder buildEMail(String eMail) {
		this.eMail = eMail;
		return this;		
	}
	
	public UserBuilder buildRole(int role) {
		this.role = role;
		return this;		
	}
	
	public User buildUser() {
		User user = new User();
		user.setId(id);
		user.setNickName(nickName);
		user.setHashPassword(hashPassword);
		user.setName(name);
		user.setSurname(surname);
		user.seteMail(eMail);
		user.setRole(role);
		return user;
	}	

}
