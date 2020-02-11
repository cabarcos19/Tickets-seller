package es.udc.ws.app.service.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapBookingUsedException",
    targetNamespace="http://soap.ws.udc.es/"
)

public class SoapBookingUsedException extends Exception{
    
	private SoapBookingUsedExceptionInfo faultInfo;

    protected SoapBookingUsedException(
            SoapBookingUsedExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public SoapBookingUsedExceptionInfo getFaultInfo() {
        return faultInfo;
    } 

}