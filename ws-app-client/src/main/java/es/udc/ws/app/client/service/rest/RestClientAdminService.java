package es.udc.ws.app.client.service.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import es.udc.ws.app.client.service.ClientAdminService;
import es.udc.ws.app.client.service.dto.ClientAdminShowDto;
import es.udc.ws.app.client.service.exceptions.*;
import es.udc.ws.app.client.service.rest.xml.XmlClientAdminShowDtoConversor;
import es.udc.ws.app.client.service.rest.xml.XmlClientExceptionConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.xml.exceptions.ParsingException;

public class RestClientAdminService implements  ClientAdminService {
	
    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientAdminService.endpointAddress";
    private String endpointAddress;

	
	@Override
	public Long createShow(ClientAdminShowDto show) throws InputValidationException {
		 try {

			 HttpResponse response = Request.Post(getEndpointAddress() + "show").
	                    bodyStream(toInputStream(show), ContentType.create("application/xml")).
	                    execute().returnResponse();

	            validateStatusCode(HttpStatus.SC_CREATED, response);

	            return XmlClientAdminShowDtoConversor.toClientAdminShowDto(response.getEntity().getContent()).getId_show();

	        } catch (InputValidationException e) {
	            throw e;
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }

	    }

	@Override
	public void updateShow(ClientAdminShowDto show) throws InputValidationException, InstanceNotFoundException, ClientMaxTicketsException, ClientDiscountedPriceException {
		try {
		HttpResponse response = Request.Put(getEndpointAddress() + "show/"
                + URLEncoder.encode(Long.toString(show.getId_show()), "UTF-8")).
                bodyStream(toInputStream(show), ContentType.create("application/xml")).
                execute().returnResponse();

        validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

        return;

		} catch (InputValidationException e) {
        throw e;
		} catch (Exception e) {
        throw new RuntimeException(e);
		}

	}

	@Override
	public ClientAdminShowDto findShow(Long id_show) throws InstanceNotFoundException {
		
		 try {
			 HttpResponse response = Request.Get(getEndpointAddress() + "show/"
                            + URLEncoder.encode(Long.toString(id_show), "UTF-8")).
                    execute().returnResponse();

             validateStatusCode(HttpStatus.SC_OK, response);
             return XmlClientAdminShowDtoConversor.toClientAdminShowDto(response.getEntity()
                    .getContent());

	     }catch (Exception e) {
	            throw new RuntimeException(e);
	     }
	}

	@Override
	public void redeeemTicket(long id_booking, String credit_card)
			throws InputValidationException, InstanceNotFoundException, ClientBookingUsedException, ClientCreditCardMatchException {
		try {
			HttpResponse response = Request.Post(getEndpointAddress() + "booking").
	                bodyForm(
	                        Form.form().
	                        add("id_booking", Long.toString(id_booking)).
	                        add("creditcardnumber", credit_card).
	                        build()).
	                execute().returnResponse();
	
	            validateStatusCode(HttpStatus.SC_NO_CONTENT, response);
	
	            return;
        } catch (InputValidationException ex) {
            throw ex;
        } catch (ClientCreditCardMatchException ex) {
            throw ex;
        } catch (Exception e) {
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
    
    private void validateStatusCode(int successCode, HttpResponse response)
            throws InstanceNotFoundException, ClientBookingUsedException,
            InputValidationException, ParsingException, ClientShowExpirationException, ClientCreditCardMatchException, ClientDiscountedPriceException, ClientMaxTicketsException {

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

                case HttpStatus.SC_CONFLICT:
                    throw XmlClientExceptionConversor.fromBookingUsedExceptionXml(
                            response.getEntity().getContent());
                    
                case HttpStatus.SC_PRECONDITION_FAILED:
                    throw XmlClientExceptionConversor.fromCreditCardMatchException(
                            response.getEntity().getContent());
                    
                case HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE:
                    throw XmlClientExceptionConversor.fromDiscountedPriceException(
                            response.getEntity().getContent());
                    
                case HttpStatus.SC_EXPECTATION_FAILED:
                    throw XmlClientExceptionConversor.fromMaxTicketsException(
                            response.getEntity().getContent());

                default:
                    throw new RuntimeException("HTTP error; status code = "
                            + statusCode);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    
    private InputStream toInputStream(ClientAdminShowDto show) {

        try {

            ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

            outputter.output(XmlClientAdminShowDtoConversor.toXml(show), xmlOutputStream);

            return new ByteArrayInputStream(xmlOutputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
