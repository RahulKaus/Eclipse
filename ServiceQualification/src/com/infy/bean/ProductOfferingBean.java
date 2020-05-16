package com.infy.bean;

public class ProductOfferingBean {
	
	private Integer id;
	private String name;
	private ProductSpecificationBean specification;
	private String message;
	public ProductSpecificationBean getSpecification() {
		return specification;
	}
	public void setSpecificationId(ProductSpecificationBean specification) {
		this.specification = specification;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
