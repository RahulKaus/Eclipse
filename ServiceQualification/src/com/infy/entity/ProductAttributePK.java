package com.infy.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProductAttributePK implements Serializable {

	private ProductAttributesEntity prEntity;
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
