package com.pojo;

public class UserPojo {
	private String username,Password,email,Phonenumber;
	
	public UserPojo(String username, String password, String email, String phonenumber) {
		super();
		this.username = username;
		this.Password = password;
		this.email = email;
		this.Phonenumber = phonenumber;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return Phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		Phonenumber = phonenumber;
	}
	

}
