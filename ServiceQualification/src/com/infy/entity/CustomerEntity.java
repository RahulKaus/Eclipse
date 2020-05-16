package com.infy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
	public class CustomerEntity 
	{
		@Id
		private Integer customerId;
		private String name;
		private String location;
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
		public Integer getCustomerId() {
			return customerId;
		}	
		public void setCustomerId(Integer customerId) {
			this.customerId = customerId;
		}
}
