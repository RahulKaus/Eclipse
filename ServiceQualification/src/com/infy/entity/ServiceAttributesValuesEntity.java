package com.infy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Service_Attribute_Values")
@IdClass(ServiceAttributePK.class)
public class ServiceAttributesValuesEntity {
	
	
	@Id
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="Id")
	private ServiceAttributesEntity srEntity;
	@Id
	private String value;
	
	public ServiceAttributesEntity getServiceAttributesEntity() {
		return srEntity;
	}
	public void setServiceAttributesEntity(
			ServiceAttributesEntity serviceAttributesEntity) {
		this.srEntity = serviceAttributesEntity;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
