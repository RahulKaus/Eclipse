package com.infy.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;
@Entity
@Table(name="Service")
public class ServiceEntity implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id; 
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="Specification_id")
	private ProductSpecificationEntity specificationEntity;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String name;
	

	
	public ProductSpecificationEntity getSpecificationEntity() {
		return specificationEntity;
	}
	public void setSpecificationEntity(
			ProductSpecificationEntity specificationEntity) {
		this.specificationEntity = specificationEntity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
