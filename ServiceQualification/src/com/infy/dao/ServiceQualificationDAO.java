package com.infy.dao;

import java.util.Map;

import com.infy.bean.CustomerBean;
import com.infy.bean.FeasibilityBean;
import com.infy.bean.UserBean;

public interface ServiceQualificationDAO {

	public Map<Integer, String> getProductList() throws Exception;
	public Map<String, Integer> generateId() throws Exception;
	public CustomerBean createCustomer(CustomerBean customer) throws Exception;
	public Map<String, String> getProductAttributes(Integer id) throws Exception;
	public Map<String, String> getServiceAttributes(Integer id) throws Exception;
	public UserBean checkUsers(UserBean userBean) throws Exception;
	public FeasibilityBean getExchange(FeasibilityBean feasable) throws Exception;
	
	
	

}