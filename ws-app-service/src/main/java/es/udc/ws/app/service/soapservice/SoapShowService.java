package es.udc.ws.app.service.soapservice;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import es.udc.ws.app.model.show.Show;
import es.udc.ws.app.model.showservice.ShowServiceFactory;
import es.udc.ws.app.model.showservice.exceptions.BookingUsedException;
import es.udc.ws.app.model.showservice.exceptions.CreditCardMatchException;
import es.udc.ws.app.model.showservice.exceptions.DiscountedPriceException;
import es.udc.ws.app.model.showservice.exceptions.MaxTicketsException;
import es.udc.ws.app.service.dto.ServiceShowDto;
import es.udc.ws.app.service.serviceutil.ShowToShowDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

@WebService(
	    name = "ShowsProvider",
	    serviceName = "ShowsProviderService",
	    targetNamespace = "http://soap.ws.udc.es/"
	)
public class SoapShowService {
    @WebMethod(
            operationName ="createShow"
        )
        public ServiceShowDto createShow(@WebParam(name = "showDto") ServiceShowDto showDto)
                throws SoapInputValidationException {
            Show show = ShowToShowDtoConversor.toShow(showDto);
            try {
            	Show createdshow=ShowServiceFactory.getService().createShow(show);
                return ShowToShowDtoConversor.toShowDto(createdshow);
            } catch (InputValidationException ex) {
                throw new SoapInputValidationException(ex.getMessage());
            }
        }
	
    @WebMethod(
            operationName = "updateShow"
        )
        public void updateShow(@WebParam(name = "showDto") ServiceShowDto showDto)
                throws SoapInputValidationException, SoapInstanceNotFoundException, DiscountedPriceException, MaxTicketsException {
            Show show = ShowToShowDtoConversor.toShow(showDto);
            try {
                ShowServiceFactory.getService().updateShow(show);
            } catch (InputValidationException ex) {
                throw new SoapInputValidationException(ex.getMessage());
            } catch (InstanceNotFoundException ex) {
                throw new SoapInstanceNotFoundException(
                        new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                                ex.getInstanceType()));
            }
        }
	
    @WebMethod(
            operationName = "findShow"
        )
        public ServiceShowDto findShow(@WebParam(name = "id_show") Long id_show)
                throws SoapInstanceNotFoundException{
            try {
                Show show = ShowServiceFactory.getService().findShow(id_show);
                return ShowToShowDtoConversor.toShowDto(show);
            } catch (InstanceNotFoundException ex) {
                throw new SoapInstanceNotFoundException(
                        new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                                ex.getInstanceType()));
            } 
        }

    @WebMethod(
            operationName = "redeemTicket"
        )
        public void redeemTicket(@WebParam(name = "id_booking") Long id_booking,
                @WebParam(name = "credit_card") String credit_card)
                throws SoapInstanceNotFoundException, SoapInputValidationException, SoapBookingUsedException, CreditCardMatchException {
            try {
                ShowServiceFactory.getService().redeeemTicket(id_booking, credit_card);
            } catch (InstanceNotFoundException ex) {
                throw new SoapInstanceNotFoundException(
                        new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                                ex.getInstanceType()));
            } catch (InputValidationException ex) {
                throw new SoapInputValidationException(ex.getMessage());
            
            }catch(BookingUsedException ex) {
            	throw new SoapBookingUsedException( 
            			new SoapBookingUsedExceptionInfo(ex.getId_booking()));
            }
            
        }
    
    
    
}