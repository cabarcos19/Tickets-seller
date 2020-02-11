package es.udc.ws.app.model.showservice;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class ShowServiceFactory {
	 private final static String CLASS_NAME_PARAMETER = "ShowServiceFactory.className";
	    private static ShowService service = null;

	    private ShowServiceFactory() {
	    }

	    @SuppressWarnings("rawtypes")
	    private static ShowService getInstance() {
	        try {
	            String serviceClassName = ConfigurationParametersManager
	                    .getParameter(CLASS_NAME_PARAMETER);
	            Class serviceClass = Class.forName(serviceClassName);
	            return (ShowService) serviceClass.newInstance();
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }

	    }

	    public synchronized static ShowService getService() {

	        if (service == null) {
	            service = getInstance();
	        }
	        return service;

	    }
}
