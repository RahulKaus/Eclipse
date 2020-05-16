package com.infy.api;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.infy.bean.CustomerBean;
import com.infy.bean.FeasibilityBean;
import com.infy.bean.ProductOfferingBean;
import com.infy.bean.UserBean;
import com.infy.resources.AppConfig;
import com.infy.resources.Factory;
import com.infy.resources.JSONParser;
import com.infy.service.ServiceQualificationService;

@Path("Service")
public class ServiceAPI {
	
	//Fetch Product Types
	@Path("fetchproducttypes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchProductList() throws Exception {
		
		String returnValue = null;
		Response response = null;
		try {
			
			ServiceQualificationService serviceQualificationService = Factory
					.createServiceQualification();
			Map<Integer, String> productOfferingMap = new HashMap<>();
			productOfferingMap = serviceQualificationService.getProductList();

			returnValue = JSONParser.toJson(productOfferingMap);
			response = Response.status(Status.OK).entity(returnValue).build();
			
		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			ProductOfferingBean productOfferingBean = new ProductOfferingBean();
			productOfferingBean.setMessage(errorMessage);
			String returnString = JSONParser.toJson(productOfferingBean);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}
	
	//Validate only Authorized Users
	@Path("validateUser")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateUser(String dataRecieved) throws Exception {
		String returnValue = null;
		Response response = null;
		try {
			UserBean userBean=JSONParser.fromJson(dataRecieved, UserBean.class);
			ServiceQualificationService serviceQualificationService = Factory
					.createServiceQualification();
			userBean = serviceQualificationService.checkUsers(userBean);
			
			returnValue = JSONParser.toJson(userBean);
			response = Response.status(Status.OK).entity(returnValue).build();
			
		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			UserBean userBean = new UserBean();
			userBean.setMessage(errorMessage);
			String returnString = JSONParser.toJson(userBean);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}

	//Generate Request Id of Customer
	@Path("getReqId")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReqId() throws Exception {
  
		String returnValue = null;
        Response response = null;
        Map<String,Integer> ans=new LinkedHashMap<String, Integer>();

           try {
                  ServiceQualificationService serviceQualificationService = Factory
                               .createServiceQualification();
                  ans = serviceQualificationService.generateId();  
                  returnValue = JSONParser.toJson(ans);
                  response = Response.status(Status.OK).entity(returnValue).build();
                  
           } catch (Exception e) {
                  String errorMessage = AppConfig.PROPERTIES.getProperty(e
                               .getMessage());
                  
                  UserBean userBean = new UserBean();
                  userBean.setMessage(errorMessage);
                  String returnString = JSONParser.toJson(userBean);
                  response = Response.status(Status.SERVICE_UNAVAILABLE)
                               .entity(returnString).build();
           }
           return response;
    }      

	
	//Fetch Service Attributes based on product selected
	@Path("fetchserviceattributes/{prodid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchServiceAttributes(@PathParam("prodid") int prodId) throws Exception {
		
		String returnValue = null;
		Response response = null;
		try {
			
			ServiceQualificationService serviceQualificationService = Factory.createServiceQualification();
			Map<String,String> serviceAttributeMap = new HashMap<String,String>();
			serviceAttributeMap=	serviceQualificationService.getServiceAttributes(prodId);
			
			returnValue = JSONParser.toJson(serviceAttributeMap);
			response = Response.status(Status.OK).entity(returnValue).build();
			
		} catch (Exception e) {
			
			String errorMessage = AppConfig.PROPERTIES.getProperty(e.getMessage());
			
			ProductOfferingBean productOfferingBean = new ProductOfferingBean();
			productOfferingBean.setMessage(errorMessage);
			String returnString = JSONParser.toJson(productOfferingBean);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}
	
	//Fetch Product Attributes based on product selected
	@Path("fetchproductattributes/{prodid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchProductAttributes(@PathParam("prodid") int prodId) throws Exception {
		
		String returnValue = null;
		Response response = null;
		try {
			ServiceQualificationService serviceQualificationService = Factory
					.createServiceQualification();
			Map<String, String> productAttributeMap = 
					new HashMap<String, String>();
			productAttributeMap = serviceQualificationService
					.getProductAttributes(prodId);
			
			returnValue = JSONParser.toJson(productAttributeMap);
			response = Response.status(Status.OK).entity(returnValue).build();
			
		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			ProductOfferingBean productOfferingBean = new ProductOfferingBean();
			productOfferingBean.setMessage(errorMessage);
			String returnString = JSONParser.toJson(productOfferingBean);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}
	
	@Path("feasibility")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response feasibility(String dataRecieved) throws Exception {
		Response response = null;
		try {

			FeasibilityBean bean  = JSONParser.fromJson(dataRecieved,FeasibilityBean.class);
		

			ServiceQualificationService serviceQualificationService = Factory
					.createServiceQualification();
			bean = serviceQualificationService.getFeasibilty(bean);

			String returnString = JSONParser.toJson(bean);
			response = Response.status(Status.OK).entity(returnString).build();

		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());
			CustomerBean customer = new CustomerBean();
			customer.setMessage(errorMessage);
			String returnString = JSONParser.toJson(customer);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}
	

	// adds customer to database
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCustomer(String dataRecieved) throws Exception {
		Response response = null;
		try {
	
			CustomerBean customer = JSONParser.fromJson(dataRecieved,
					CustomerBean.class);
	
			ServiceQualificationService serviceQualificationService = Factory
					.createServiceQualification();
			customer = serviceQualificationService.createCustomer(customer);
			String successMessage = this.getSuccessMessage(customer);
	
			customer.setMessage(successMessage);
			String returnString = JSONParser.toJson(customer);
			response = Response.status(Status.OK).entity(returnString).build();
	
		} catch (Exception e) {
			
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());
			CustomerBean customer = new CustomerBean();
			customer.setMessage(errorMessage);
			String returnString = JSONParser.toJson(customer);
	
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
	
		return response;
	}

	private String getSuccessMessage(CustomerBean customer) {
		String message = AppConfig.PROPERTIES
				.getProperty("SERVICE.ADDED_SUCCESS");
		message += customer.getCustomerId();

		return message;
	}
}
