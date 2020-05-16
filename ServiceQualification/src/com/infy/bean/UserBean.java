package com.infy.bean;

public class UserBean {
	private Integer id;
	private String name;
	private String password;
	private String message;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setMessage(String message) {
		this.message=message;
	}
	public String getMessage() {
		return message;
	}
}
