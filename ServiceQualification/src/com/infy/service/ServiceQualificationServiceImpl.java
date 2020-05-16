package com.infy.service;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.infy.bean.CustomerBean;
import com.infy.bean.FeasibilityBean;
import com.infy.bean.UserBean;
import com.infy.dao.ServiceQualificationDAO;
import com.infy.resources.AppConfig;
import com.infy.resources.Factory;
public class ServiceQualificationServiceImpl implements ServiceQualificationService {

	//for getting product list (id and name)
	public Map<Integer, String> getProductList() throws Exception {
		
		Map<Integer,String> productOfferingMap = new HashMap<Integer,String>();
		try {
			
			ServiceQualificationDAO serviceQualificationDAO = Factory.createServiceQualificationDAO();
			productOfferingMap =serviceQualificationDAO.getProductList();
		
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return productOfferingMap;
	}

	//to get service attribute values for a corresponding id
	public Map<String,String> getServiceAttributes(Integer id) throws Exception{
		
		Map<String,String> serviceAttributeMap = new HashMap<String,String>();
		try {
			
			ServiceQualificationDAO serviceQualificationDAO = Factory.createServiceQualificationDAO();
			serviceAttributeMap =serviceQualificationDAO.getServiceAttributes(id);
		
		} catch (Exception exception) {
			if(!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return serviceAttributeMap;
	}
	
	//to generate the request id when the customer is created
    public Map<String,Integer> generateId() throws Exception{

        Map<String,Integer> ans=new LinkedHashMap<String, Integer>();
        try {
        	
               ServiceQualificationDAO serviceQualificationDAO = Factory.createServiceQualificationDAO();
               ans =serviceQualificationDAO.generateId();
               
        } catch (Exception exception) {
               if (!exception.getMessage().contains("DAO")) {
                     Logger logger = Logger.getLogger(this.getClass());
                     logger.error(exception.getMessage(), exception);
               }
               throw exception;
        }
        return ans;
        
    }

	//to get users from DB for validation
	public UserBean checkUsers(UserBean userBean) throws Exception {
		
		try {
			ServiceQualificationDAO serviceQualificationDAO = Factory.createServiceQualificationDAO();
			userBean=serviceQualificationDAO.checkUsers(userBean);
			
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return userBean;
	}
	
	//to get product attribute values for a corresponding id
	public Map<String, String> getProductAttributes(Integer id) throws Exception {
		
		Map<String, String> productAttributeMap = new HashMap<>();
		
		try {
			ServiceQualificationDAO serviceQualificationDAO = Factory.createServiceQualificationDAO();
			productAttributeMap =serviceQualificationDAO.getProductAttributes(id);
		
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return productAttributeMap;
	}

	// to store the customer details
	public CustomerBean createCustomer(CustomerBean customer) throws Exception {
		
		CustomerBean createdCustomerDetails = null;
		try {
			
			ServiceQualificationDAO customerDAO = Factory.createServiceQualificationDAO();
			createdCustomerDetails = customerDAO.createCustomer(customer);
			
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return createdCustomerDetails;
	}
	
	//to check feasibility of the requested product
	public FeasibilityBean getFeasibilty(FeasibilityBean feasable) throws Exception{
		
		try{
			ServiceQualificationDAO feasibilityDAO = Factory.createServiceQualificationDAO();
			FeasibilityBean fBean=feasibilityDAO.getExchange(feasable);
			
			if(feasable.getDistance()<=fBean.getMaxDistance()){
				if(feasable.getAccessSpeed()<=fBean.getMaxSpeed()){
					//executes if both condition are true
					feasable.setResult(AppConfig.PROPERTIES.getProperty("FEASIBILITY.SUCCESS"));
					feasable.setComments(AppConfig.PROPERTIES.getProperty("FEASIBILITY.COMMENTSUCCESS"));
				}
				else{
					//executes if requested access speed can not be provide
					feasable.setResult(AppConfig.PROPERTIES.getProperty("FEASIBILITY.FAILURE"));
					feasable.setComments(AppConfig.PROPERTIES.getProperty("FEASIBILITY.COMMENTSPEED1")
							+fBean.getLocationName()+AppConfig.PROPERTIES.getProperty("FEASIBILITY.COMMENTSPEED2")
							+fBean.getMaxSpeed()+" Kbps");
				}
			}
			else{
				//executes if the requested cable length is more than max allowed distance from exchange
				feasable.setResult(AppConfig.PROPERTIES.getProperty("FEASIBILITY.FAILURE"));
				feasable.setComments(AppConfig.PROPERTIES.getProperty("FEASIBILITY.COMMENTDISTANCE")						
						+fBean.getMaxDistance()+"m");
				
			}
		}
		catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return feasable;
	}
}