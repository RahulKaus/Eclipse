package com.infy.resources;

import org.apache.log4j.Logger;

import com.infy.dao.ServiceQualificationDAOImpl;
import com.infy.service.ServiceQualificationServiceImpl;



public class Factory {

	public static ServiceQualificationServiceImpl createServiceQualification() {
		Logger logger = Logger.getLogger(Factory.class);
		logger.info("FactoryService: Inside ServiceQualificationServiceImpl Method");
		return new ServiceQualificationServiceImpl();
	}

	public static ServiceQualificationDAOImpl createServiceQualificationDAO() {
		Logger logger = Logger.getLogger(Factory.class);
		logger.info("FactoryDAO: Inside ServiceQualificationDAOImpl Method");
		return new ServiceQualificationDAOImpl();
	}
}
