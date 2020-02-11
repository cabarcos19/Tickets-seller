package es.udc.ws.app.service.restservices.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import es.udc.ws.app.model.booking.Booking;
import es.udc.ws.app.model.showservice.ShowServiceFactory;
import es.udc.ws.app.model.showservice.exceptions.BookingUsedException;
import es.udc.ws.app.model.showservice.exceptions.CreditCardMatchException;
import es.udc.ws.app.model.showservice.exceptions.ShowExpirationException;
import es.udc.ws.app.model.showservice.exceptions.SoldOutTicketsException;
import es.udc.ws.app.service.dto.ServiceBookingDto;
import es.udc.ws.app.service.restservice.xml.*;
import es.udc.ws.app.service.serviceutil.BookingToBookingDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class BookingServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path != null && path.length() > 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);
            return;
        }
        String showIdParameter = req.getParameter("id_show");
        if (showIdParameter == null) {
        	String id_booking = req.getParameter("id_booking");
            if (id_booking == null) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        XmlServiceExceptionConversor.toInputValidationExceptionXml(
                                new InputValidationException("Invalid Request: " + "parameter 'id_booking' or 'id_show' is mandatory")),
                        null);
                return;
            }

            String credit_card = req.getParameter("creditcardnumber");
            if (credit_card == null) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        XmlServiceExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
                                "Invalid Request: " + "parameter 'creditCardNumber' is mandatory")),
                        null);

                return;
            }
            try {
            	ShowServiceFactory.getService().redeeemTicket(Long.parseLong(id_booking), credit_card);
            } catch (InputValidationException ex) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        XmlServiceExceptionConversor.toInputValidationExceptionXml(ex), null);
                return;
            } catch (InstanceNotFoundException ex) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                        XmlServiceExceptionConversor.toInstanceNotFoundException(ex), null);
                return;
        	} catch (BookingUsedException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CONFLICT,
                    XmlServiceExceptionConversor.toBookingUsedException(ex), null);
            	return;
        	}catch (CreditCardMatchException ex) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_PRECONDITION_FAILED,
                        XmlServiceExceptionConversor.toCreditCardMatchException(ex), null);
                return;
        	}
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
            return;
        }else {
	        Long id_show;
	        try {
	            id_show = Long.valueOf(showIdParameter);
	        } catch (NumberFormatException ex) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                            XmlServiceExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
	                                    "Invalid Request: " + "parameter 'id_show' is invalid '" + showIdParameter + "'")),null);
	
	            return;
	        }
	        String email = req.getParameter("email");
	        if (email == null) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
	                            new InputValidationException("Invalid Request: " + "parameter 'email' is mandatory")),
	                    null);
	            return;
	        }
	        String creditCardNumber = req.getParameter("creditcardnumber");
	        if (creditCardNumber == null) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                    XmlServiceExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
	                            "Invalid Request: " + "parameter 'creditCardNumber' is mandatory")),
	                    null);
	
	            return;
	        }
	        
	        String ticketParameter = req.getParameter("ticket");
	        if (ticketParameter == null) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
	                            new InputValidationException("Invalid Request: " + "parameter 'ticket' is mandatory")),
	                    null);
	            return;
	        }
	        int ticket;
	        try {
	            ticket = Integer.valueOf(ticketParameter);
	        } catch (NumberFormatException ex) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                            XmlServiceExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
	                                    "Invalid Request: " + "parameter 'ticket' is invalid '" + ticketParameter + "'")),null);
	
	            return;
	        }
	        
	        
	        long id_booking;
	        Booking booking = null;
	        List<Booking> list_bookings=new ArrayList<>();; 
	        ServiceBookingDto booking_dto;
	        try {
	        	id_booking = ShowServiceFactory.getService().booking(id_show, email, creditCardNumber, ticket);
	        } catch (InstanceNotFoundException ex) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
	                    XmlServiceExceptionConversor.toInstanceNotFoundException(ex), null);
	            return;
	        } catch (InputValidationException ex) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                    XmlServiceExceptionConversor.toInputValidationExceptionXml(ex), null);
	            return;
	        } catch (ShowExpirationException ex) {
	        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
	                XmlServiceExceptionConversor.toShowExpirationException(ex), null);
	        return;
	        } catch (SoldOutTicketsException ex) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN,
	                    XmlServiceExceptionConversor.toSoldOutTicketsException(ex), null);
	            return;
	        } 
	        
	        try {
	        list_bookings=ShowServiceFactory.getService().findBookings(email);
	        } catch (InputValidationException ex) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                    XmlServiceExceptionConversor.toInputValidationExceptionXml(ex), null);
	            return;
	        }
	        for(int i=0;i<list_bookings.size();i++) {
	        	if (list_bookings.get(i).getId_booking()==id_booking)
	        		booking=list_bookings.get(i);
	        }
	    	booking_dto= BookingToBookingDtoConversor.toBookingDto(booking);
	        
	        String bookingURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/?email=" + email;
	
	        Map<String, String> headers = new HashMap<>(1);
	        headers.put("Location", bookingURL);
	
	        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
	                XmlServiceBookingDtoConversor.toResponse(booking_dto), headers);
	    }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path != null && path.length() > 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);
            return;
        }
        String id_booking = req.getParameter("id_booking");
        if (id_booking == null) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
                            new InputValidationException("Invalid Request: " + "parameter 'id_show' is mandatory")),
                    null);
            return;
        }

        String credit_card = req.getParameter("creditcardnumber");
        if (credit_card == null) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
                            "Invalid Request: " + "parameter 'creditCardNumber' is mandatory")),
                    null);

            return;
        }
        try {
        	ShowServiceFactory.getService().redeeemTicket(Long.parseLong(id_booking), credit_card);
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(ex), null);
            return;
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    XmlServiceExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
    	} catch (BookingUsedException ex) {
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CONFLICT,
                XmlServiceExceptionConversor.toBookingUsedException(ex), null);
        	return;
    	}catch (CreditCardMatchException ex) {
            ServletUtils.writeServiceResponse(resp, HttpStatus.SC_PRECONDITION_FAILED,
                    XmlServiceExceptionConversor.toCreditCardMatchException(ex), null);
            return;
    	}
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
                            new InputValidationException("Invalid Request: " + "invalid booking id")),
                    null);
            return;
        }
        String bookingEmail = path.substring(1);
        try {
            if (bookingEmail ==null)
            	throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
                            new InputValidationException("Invalid Request: " + "invalid booking id '" + bookingEmail)),
                    null);
            return;
        }
        List<Booking> list_bookings; 
        List<ServiceBookingDto> list_booking_dto=new ArrayList<>();
        try {
            list_bookings = ShowServiceFactory.getService().findBookings(bookingEmail);
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(ex), null);
            return;
        }

        for(int i=0;i<list_bookings.size();i++) {
        	list_booking_dto.add(BookingToBookingDtoConversor.toBookingDto(list_bookings.get(i)));
        }

        List<ServiceBookingDto> bookingDtos = BookingToBookingDtoConversor.toBookingDtos(list_bookings);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                XmlServiceBookingDtoConversor.toResponse(bookingDtos), null);

    }
}