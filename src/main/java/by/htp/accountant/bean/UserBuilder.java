package by.htp.accountant.bean;

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
	
	public UserBuilder buildHashFromPassword(String password) {
		
		this.hashPassword = HashPasswordMaker.getInstance().getHashPassword(password);
		
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
		if (id != 0) user.setId(id);
		if (nickName != null) user.setNickName(nickName);
		if (hashPassword != null) user.setHashPassword(hashPassword);
		if (name != null) user.setName(name);
		if (surname != null) user.setSurname(surname);
		if (eMail != null) user.seteMail(eMail);
		if (role != 0) user.setRole(role);
		return user;
	}	

}
