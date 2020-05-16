package com.infy.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.infy.bean.CustomerBean;
import com.infy.bean.FeasibilityBean;
import com.infy.bean.UserBean;
import com.infy.cache.Cache;
import com.infy.entity.CustomerEntity;
import com.infy.entity.ExchangeEntity;
import com.infy.entity.ProductOfferingEntity;
import com.infy.entity.UsersEntity;
import com.infy.resources.AppConfig;
import com.infy.resources.HibernateUtility;


public class ServiceQualificationDAOImpl implements ServiceQualificationDAO {

	
	//for getting product list (id and name)
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getProductList() throws Exception{
		
		SessionFactory sessionFactory =null;
		Session session = null;
		List<ProductOfferingEntity> productOfferingEntities = new LinkedList<ProductOfferingEntity>();
		Map<Integer,String> productOfferingMap = new HashMap<>();
		
		String hql = "from ProductOfferingEntity";
		
		
		try {
			sessionFactory = HibernateUtility.createSessionFactory();
			session = sessionFactory.openSession();
			
			// Creating query object
			Query query = session.createQuery(hql);
			// Executing the query
			productOfferingEntities = query.list();
				
			//adding to a map
			for (ProductOfferingEntity product : productOfferingEntities) {
				productOfferingMap.put(product.getId(),product.getName());
			}
			
		} catch (HibernateException exception) {
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception); 
			throw new Exception("DAO.TECHNICAL_ERROR");
		} 
		catch (Exception exception) {
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
			throw exception;
		}
		finally {
			if(session.isOpen()|| session!=null){
				session.close();
			}		
		}
		return productOfferingMap;	
	}
	
	//to get users from DB for validation
	@SuppressWarnings("unchecked")
	public UserBean checkUsers(UserBean userBean) throws Exception{
		
		SessionFactory sessionFactory =null;
		Session session = null;
		List<UsersEntity> users = new LinkedList<UsersEntity>();
		String hql = "from UsersEntity";

		try {
			sessionFactory = HibernateUtility.createSessionFactory();
			session = sessionFactory.openSession();
			// Creating query object
			Query query = session.createQuery(hql);
			// Executing the query
			users = query.list();
			int flag=0;
			for(UsersEntity u:users){

				if(u.getName().equals(userBean.getName()) && u.getPassword().equals(userBean.getPassword())){
					flag=1;
					break;
				}
			}
			
			if(flag==0)
				userBean.setMessage(AppConfig.PROPERTIES.getProperty("USER.FAILURE"));
			
		} catch (HibernateException exception) {
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception); 
			throw new Exception("DAO.TECHNICAL_ERROR");
		} 
		catch (Exception exception) {
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
			throw exception;
		}
		finally {
			if(session.isOpen()|| session!=null){
				session.close();
			}		
		}
		return userBean;
	}
	
	public Map<String,String> getFromCache(String id) throws InterruptedException{
		Cache<String,Map<String,String>> cache = new Cache<String,Map<String,String>>(300, 500);
		if(cache.get(id)!=null){
	    	return cache.get(id);
	    }
		else
		return null;
	}
	
	public void putToCache(String id,Map<String,String> value) throws InterruptedException{
		Cache<String,Map<String,String>> cache = new Cache<String,Map<String,String>>(300, 500);
		cache.put(id, value);
		
	}
	
	
	//to get product attribute values for a corresponding id
	@SuppressWarnings("unchecked")
	public Map<String, String> getProductAttributes(Integer id) throws Exception{

		Map<String,String> productAttributeMap = new LinkedHashMap<>();
	    String pKey="p"+id.toString();
	    if(getFromCache(pKey)!=null){
	    	 productAttributeMap=getFromCache(pKey);
	    	return productAttributeMap;
	    }
	    	
	    
	    else
	    {
		
        SessionFactory sessionFactory =null;
        Session session = null;
        List<Object[]> productAttributeEntities = new LinkedList<Object[]>();
        List<String> attributeValue= new ArrayList<>();
        
        //joining product attribute value table with product attribute table and product offering table
        String hql = "select po.id,pa.name,pav.value from ProductAttributesValuesEntity pav "
                     + "inner join pav.prEntity pa "
                    + "inner join pa.offeringEntity po where po.id=?";
        
        try {
               sessionFactory = HibernateUtility.createSessionFactory();
               session = sessionFactory.openSession();

               // Creating query object
               Query query = session.createQuery(hql);
               query.setParameter(0, id);


               // Executing the query
               productAttributeEntities = query.list();
       
               //to get the product attributes name as keys in the map
               for (Object[] product : productAttributeEntities) 
            	   productAttributeMap.put(product[1].toString(), "");
   			
             //loop runs for each key got in previous step to add values
   			for(String key:productAttributeMap.keySet()){
   				
   				for(Object[] product:productAttributeEntities){
   					//adds the value to a list if key matches
					if(product[1].toString().equals(key))
						attributeValue.add("\'"+product[2].toString()+"\'");
					}
   				
   				//adds the list to the corresponding key in the map
   				productAttributeMap.put(key, attributeValue.toString());	
				attributeValue.clear();		
				}			//end of for loop
   			
        } catch (HibernateException exception) {
               DOMConfigurator.configure("src/resources/log4j.xml");
               Logger logger = Logger.getLogger(this.getClass());
               logger.error(exception.getMessage(), exception);
               throw new Exception("DAO.TECHNICAL_ERROR");
        }

        catch (Exception exception) {
               DOMConfigurator.configure("src/resources/log4j.xml");
               Logger logger = Logger.getLogger(this.getClass());
               logger.error(exception.getMessage(), exception);
               throw exception;
        }

        finally {
               if(session.isOpen()|| session!=null){
                    session.close();
               }            
        }
        putToCache(pKey, productAttributeMap);
        return productAttributeMap;
	    }
 }
	
	
	//to generate the request id when the customer is created
	public Map<String,Integer> generateId() throws Exception{
        
        SessionFactory sessionFactory =null;
        Session session = null;
        Integer custId=null;
        Map<String,Integer> ans=new LinkedHashMap<>();  //map to store the requested cust id 
        
        String hql = "select max(customerId) from CustomerEntity";
        try {
               sessionFactory = HibernateUtility.createSessionFactory();
               session = sessionFactory.openSession();
               // Creating query object
               Query query = session.createQuery(hql);
               // Executing the query
               custId = (Integer)query.uniqueResult();
               custId+=1;
               ans.put("CustomerId", custId);
               
        } catch (HibernateException exception) {
               DOMConfigurator.configure("src/resources/log4j.xml");
               Logger logger = Logger.getLogger(this.getClass());
               logger.error(exception.getMessage(), exception); 
               throw new Exception("DAO.TECHNICAL_ERROR");
        } 
        catch (Exception exception) {
               DOMConfigurator.configure("src/resources/log4j.xml");
               Logger logger = Logger.getLogger(this.getClass());
               logger.error(exception.getMessage(), exception);
               throw exception;
        }
        finally {
               if(session.isOpen()|| session!=null){
                     session.close();
               }             
        }
        return ans;
 }
 
	//to get service attribute values for a corresponding id
	@SuppressWarnings("unchecked")
	public Map<String,String> getServiceAttributes(Integer id) throws Exception{
		
		Map<String,String> ansMap = new LinkedHashMap<>();
	    String sKey="s"+id.toString();
	    if(getFromCache(sKey)!=null){
	    	ansMap=getFromCache(sKey);
	    	return ansMap;
	    }
	    	
	    
	    else
	    {
		
		SessionFactory sessionFactory =null;
		Session session = null;
		
		List<Object[]> queryValue=new LinkedList<>();
		List<String> attributeValue= new ArrayList<>();
		
		 //joining service attribute value table with service attribute table and service table
		String hql = "select se.id,se.name,sa.name,sav.value from ServiceAttributesValuesEntity sav "
				+ "inner join sav.srEntity sa inner join sa.serviceEntity se where se.id=?";		
		

		try {
			sessionFactory = HibernateUtility.createSessionFactory();
			session = sessionFactory.openSession();	
			
			Query query = session.createQuery(hql);
			query.setParameter(0, id);
			queryValue = query.list();
		
			//to add the service id as key
			ansMap.put(queryValue.get(0)[0].toString(),"");
			
			//to add the service attribute names as key in the map
			for(Object[] sv:queryValue)
				ansMap.put(sv[2].toString(), "");	
	
			//to add the values in map
			for(String key:ansMap.keySet()){
				
				//adds the service name to the service id key
				if(key.equals(id.toString())){
					attributeValue.add("\'"+queryValue.get(0)[1].toString()+"\'");
					ansMap.put(key, attributeValue.toString());	
					attributeValue.clear();
				}
				//adds the attribute values to corresponding key
				else{
					for(Object[] attri:queryValue){
						if(attri[2].toString().equals(key))
							attributeValue.add("\'"+attri[3].toString()+"\'");
						}
					ansMap.put(key, attributeValue.toString());	
					attributeValue.clear();
				}
			}		
			
		} catch (HibernateException exception) {
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception); 
			throw new Exception("DAO.TECHNICAL_ERROR");
		} 
		catch (Exception exception) {
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
			throw exception;
		}
		finally {
			if(session.isOpen()|| session!=null){
				session.close();
			}		
		}
		putToCache(sKey, ansMap);
		return ansMap;
	    }
			
	}
	
//to get the exchange table and get the required fields 	
public FeasibilityBean getExchange(FeasibilityBean feasable) throws Exception{
		
		SessionFactory sessionFactory =null;
		Session session = null;
		ExchangeEntity exchange=new ExchangeEntity();
		
		String hql = "from ExchangeEntity where name=?";
		String hqlProductName = "select name from ProductOfferingEntity where id=?";
		String hqlServiceName = "select name from ServiceEntity where id=?";
		
		try {
			sessionFactory = HibernateUtility.createSessionFactory();
			session = sessionFactory.openSession();	
			
			//to set max distance and max speed at the required location
			Query query = session.createQuery(hql);
			query.setParameter(0, feasable.getLocationName());
			exchange= (ExchangeEntity)query.uniqueResult();
			
			feasable.setMaxDistance(exchange.getMaxDistance());
			feasable.setMaxSpeed(exchange.getMaxSpeed());
			
			//to set product name of the requested id
			Query queryProName = session.createQuery(hqlProductName);
			queryProName.setParameter(0, feasable.getProdId());
			feasable.setProductName((String) queryProName.uniqueResult());
			
			//to set service name of the requested id
			Query querySerName = session.createQuery(hqlServiceName);
			querySerName.setParameter(0, feasable.getProdId());
			feasable.setServiceName((String) querySerName.uniqueResult());	
			
		} catch (HibernateException exception) {
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception); 
			throw new Exception("DAO.TECHNICAL_ERROR");
		} 
		catch (Exception exception) {
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
			throw exception;
		}
		finally {
			if(session.isOpen()|| session!=null){
				session.close();
			}		
		}
		return feasable;
	}
	
	// to store the customer details
	public CustomerBean createCustomer(CustomerBean customer)throws Exception {
	
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
	
		try {
			session = sessionFactory.openSession();
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setCustomerId(customer.getCustomerId());
			customerEntity.setName(customer.getName());
			customerEntity.setLocation(customer.getLocation());
		
			//begin the transaction
			session.beginTransaction();
			session.save(customerEntity);
			session.getTransaction().commit();
			
			customer.setCustomerId(customerEntity.getCustomerId());
			return customer;
	
		} catch (HibernateException exception) {
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception); 
			throw new Exception("DAO.TECHNICAL_ERROR");
			
		} catch (Exception exception) {
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
			throw exception;
	
		} finally {
			if (session != null) 
				session.close();
		}
	}
}
