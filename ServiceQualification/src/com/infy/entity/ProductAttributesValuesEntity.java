package com.infy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Product_Attribute_Values")
@IdClass(ProductAttributePK.class)
public class ProductAttributesValuesEntity {
	
	@Id
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="Id")
	private ProductAttributesEntity prEntity;
	@Id
	private String value;
	
	public ProductAttributesEntity getPrEntity() {
		return prEntity;
	}
	public void setPrEntity(ProductAttributesEntity prEntity) {
		this.prEntity = prEntity;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
