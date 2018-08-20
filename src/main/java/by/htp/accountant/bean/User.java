package by.htp.accountant.bean;

import java.io.Serializable;

import by.htp.accountant.exception.UtilException;
import by.htp.accountant.util.HashPasswordMaker;


public class User implements Serializable{
		
	private static final long serialVersionUID = 1L;
	
	HashPasswordMaker passwordMaker = HashPasswordMaker.getInstance();
	
	private int id;
	private String nickName;
	private String hashPassword;
	private String name;
	private String surname;
	private String eMail;
	
	/**
	 * 3 role 1 administrator, 2 user, guest without number
	 */
	private int role;
	
	
	public User() {
		this.role = 2;
	}	
	
	public User(String nickName, String passwordFromUser) throws UtilException {
		
		this.nickName = nickName;
		this.hashPassword = passwordMaker.getHashPassword(passwordFromUser);
		this.name = null;
		this.surname = null;
		this.eMail = null;
		
		this.role = 2;
	}
	
	public User(String nickName, String passwordFromUser, String name, String surname, String eMail) throws UtilException {  
		
		this.nickName = nickName;
		this.hashPassword = passwordMaker.getHashPassword(passwordFromUser);
		this.name = name;
		this.surname = surname;
		this.eMail = eMail;
		this.role = 2;
		
	}

	public User(int id, String nickName, String hashPasswordFromDB, String name, String surname, String eMail, int role) {
		
		this.id = id;
		this.nickName = nickName;
		this.hashPassword = hashPasswordFromDB;
		this.name = name;
		this.surname = surname;
		this.eMail = eMail;
		this.role = role;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getHashPassword() {
		return hashPassword;
	}


	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String geteMail() {
		return eMail;
	}


	public void seteMail(String eMail) {
		this.eMail = eMail;
	}	


	public int getRole() {
		return role;
	}


	public void setRole(int role) {
		this.role = role;
	}	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eMail == null) ? 0 : eMail.hashCode());
		result = prime * result + ((hashPassword == null) ? 0 : hashPassword.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result + role;
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (eMail == null) {
			if (other.eMail != null)
				return false;
		} else if (!eMail.equals(other.eMail))
			return false;
		if (hashPassword == null) {
			if (other.hashPassword != null)
				return false;
		} else if (!hashPassword.equals(other.hashPassword))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (role != other.role)
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "User [nickName= " + nickName + ", name= " + name + ", surname= " + surname + ", eMail= " + eMail + "]";
	}	
	

}
