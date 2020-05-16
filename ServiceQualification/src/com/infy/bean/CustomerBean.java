package com.infy.bean;

public class CustomerBean {
	
	private Integer customerId;
	private String name;
	private String location;
	private String message;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMessage() {
		return message;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public void setMessage(String message) {
		this.message=message;
		// TODO Auto-generated method stub
		
	}
}
