package com.infy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="Service_Attributes")
public class ServiceAttributesEntity {
	@Id
	private Integer id;
	private String name;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="Type")
	private ServiceEntity serviceEntity;
	
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
	public ServiceEntity getOfferingEntity() {
		return serviceEntity;
	}
	public void setOfferingEntity(ServiceEntity serviceEntity) {
		this.serviceEntity = serviceEntity;
	}
	
	
	

}
