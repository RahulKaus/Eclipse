package com.infy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="Product_Attributes")
public class ProductAttributesEntity {
	@Id
	private Integer id;
	private String name;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="Type")
	private ProductOfferingEntity offeringEntity;
	
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
	public ProductOfferingEntity getOfferingEntity() {
		return offeringEntity;
	}
	public void setOfferingEntity(ProductOfferingEntity offeringEntity) {
		this.offeringEntity = offeringEntity;
	}
	

}
