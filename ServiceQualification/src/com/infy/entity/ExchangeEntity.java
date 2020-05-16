package com.infy.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="exchange")
public class ExchangeEntity {
	@Id
	private Integer id;
	private String name;
	private Integer maxSpeed;
	private Integer maxDistance;

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
	public Integer getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(Integer maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public Integer getMaxDistance() {
		return maxDistance;
	}
	public void setMaxDistance(Integer maxDistance) {
		this.maxDistance = maxDistance;
	}
	

}
