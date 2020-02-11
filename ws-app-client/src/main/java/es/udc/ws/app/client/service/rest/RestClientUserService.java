package es.udc.ws.app.client.service.rest;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import es.udc.ws.app.client.service.ClientUserService;
import es.udc.ws.app.client.service.dto.ClientBookingDto;
import es.udc.ws.app.client.service.dto.ClientUserFindDto;
import es.udc.ws.app.client.service.exceptions.ClientBookingUsedException;
import es.udc.ws.app.client.service.exceptions.ClientShowExpirationException;
import es.udc.ws.app.client.service.exceptions.ClientSoldOutTicketsException;
import es.udc.ws.app.client.service.rest.xml.XmlClientUserFindDtoConversor;
import es.udc.ws.app.client.service.rest.xml.XmlClientBookingDtoConversor;
import es.udc.ws.app.client.service.rest.xml.XmlClientExceptionConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.xml.exceptions.ParsingException;

public class RestClientUserService implements ClientUserService {
	
    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientUserService.endpointAddress";
    private String endpointAddress;


	@Override
	public List<ClientUserFindDto> findKey(String Key) {
		try {
			HttpResponse response = Request.Get(getEndpointAddress() + "find?keywords="
                    + URLEncoder.encode(Key, "UTF-8")).
            execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);
            return XmlClientUserFindDtoConversor.toClientShowDtos(response.getEntity()
                   .getContent());

	     }catch (Exception e) {
	            throw new RuntimeException(e);
	     }
	}

		@Override
		public List<ClientBookingDto> findEmail(String email) throws InputValidationException{
			try {
				HttpResponse response = Request.Get(getEndpointAddress() + "booking/"
		                + URLEncoder.encode(email, "UTF-8")).
		        execute().returnResponse();
		
		        validateStatusCode(HttpStatus.SC_OK, response);
		        return XmlClientBookingDtoConversor.toClientBookingDtos(response.getEntity()
		               .getContent());
	     }catch (Exception e) {
	            throw new RuntimeException(e);
	     }
		}
	    
		@Override
		public ClientBookingDto book(ClientBookingDto booking) throws InputValidationException, InstanceNotFoundException {
			try {

				HttpResponse response = Request.Post(getEndpointAddress() + "booking").
	                    bodyForm(
	                            Form.form().
	                            add("id_show", Long.toString(booking.getId_show())).
	                            add("email", booking.getEmail()).
	                            add("creditcardnumber", booking.getCredit_card()).
	                            add("ticket", Integer.toString(booking.getNum_tickets())).
	                            build()).
	                    execute().returnResponse();

		            validateStatusCode(HttpStatus.SC_CREATED, response);

		            return XmlClientBookingDtoConversor.toClientBookingDto(response.getEntity().getContent());

		        } catch (InputValidationException e) {
		            throw e;
		        } catch (Exception e) {
		            throw new RuntimeException(e);
		        }

		    }
		

	    private void validateStatusCode(int successCode, HttpResponse response)
	            throws InstanceNotFoundException, ClientShowExpirationException,
	            InputValidationException, ParsingException, ClientBookingUsedException, ClientSoldOutTicketsException {

	        try {

	            int statusCode = response.getStatusLine().getStatusCode();

	            /* Success? */
	            if (statusCode == successCode) {
	                return;
	            }

	            /* Handler error. */
	            switch (statusCode) {

	                case HttpStatus.SC_NOT_FOUND:
	                    throw XmlClientExceptionConversor.fromInstanceNotFoundExceptionXml(
	                            response.getEntity().getContent());

	                case HttpStatus.SC_BAD_REQUEST:
	                    throw XmlClientExceptionConversor.fromInputValidationExceptionXml(
	                            response.getEntity().getContent());

	                case HttpStatus.SC_GONE:
	                    throw XmlClientExceptionConversor.fromSaleExpirationExceptionXml(
	                            response.getEntity().getContent());
	                    
	                case HttpStatus.SC_CONFLICT:
	                    throw XmlClientExceptionConversor.fromBookingUsedExceptionXml(
	                            response.getEntity().getContent());
	                
	                case HttpStatus.SC_FORBIDDEN:
	                    throw XmlClientExceptionConversor.fromSoldOutTicketsExceptionXml(
	                            response.getEntity().getContent());

	                default:
	                    throw new RuntimeException("HTTP error; status code = "
	                            + statusCode);
	            }

	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }

	    }
	    
	    private synchronized String getEndpointAddress() {
	        if (endpointAddress == null) {
	            endpointAddress = ConfigurationParametersManager
	                    .getParameter(ENDPOINT_ADDRESS_PARAMETER);
	        }
	        return endpointAddress;
	    }

}
