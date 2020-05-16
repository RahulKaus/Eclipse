package com.infy.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServiceAttributePK implements Serializable {

	private ServiceAttributesEntity srEntity;
	private String value;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public ServiceAttributesEntity getSrEntity() {
		return srEntity;
	}
	public void setSrEntity(ServiceAttributesEntity srEntity) {
		this.srEntity = srEntity;
	}
}
