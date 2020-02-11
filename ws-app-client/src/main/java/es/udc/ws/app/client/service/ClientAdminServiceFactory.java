package es.udc.ws.app.client.service;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class ClientAdminServiceFactory {

		 private final static String CLASS_NAME_PARAMETER = "ClientAdminServiceFactory.className";
		 private static Class<ClientAdminService> serviceClass = null;

		 private ClientAdminServiceFactory() {
		 }

		 @SuppressWarnings("unchecked")
		 private synchronized static Class<ClientAdminService> getServiceClass() {

			 if (serviceClass == null) {
				 try {
					 String serviceClassName = ConfigurationParametersManager
	                 .getParameter(CLASS_NAME_PARAMETER);
					 serviceClass = (Class<ClientAdminService>) Class.forName(serviceClassName);
				 } catch (Exception e) {
					 throw new RuntimeException(e);
				 }
			 }
			 return serviceClass;

		 }

		 public static ClientAdminService getService() {

			 try {
				 return (ClientAdminService) getServiceClass().newInstance();
			 } catch (InstantiationException | IllegalAccessException e) {
				 throw new RuntimeException(e);
			 }

		 }
	
}
