package es.udc.ws.app.client.service.rest.xml;
	
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import es.udc.ws.app.client.service.dto.ClientBookingDto;
import es.udc.ws.util.xml.exceptions.ParsingException;

	public class XmlClientBookingDtoConversor {

	    public final static Namespace XML_NS = Namespace
	            .getNamespace("http://ws.udc.es/bookings/xml");

	    public static Document toResponse(ClientBookingDto sale)
	            throws IOException {

	        Element bookingElement = toXml(sale);

	        return new Document(bookingElement);
	    }

	    public static Element toXml(ClientBookingDto booking) {

	        Element bookingElement = new Element("booking", XML_NS);

	        if (booking.getId_booking() > 0) {
	            Element booking_idElement = new Element("id_booking", XML_NS);
	            booking_idElement.setText(Long.toString(booking.getId_booking()));
	            bookingElement.addContent(booking_idElement);
	        }
	        
	        Element emailElement = new Element("email", XML_NS);
	        emailElement.setText(booking.getEmail());
	        bookingElement.addContent(emailElement);
	        
	        Element credit_cardElement = new Element("creditcardnumber", XML_NS);
	        credit_cardElement.setText(booking.getCredit_card());
	        bookingElement.addContent(credit_cardElement);
	        
	        Element date_time_bookElement = new Element("date_time_book", XML_NS);
	        date_time_bookElement.setText(booking.getDate_time_book().toString());
	        bookingElement.addContent(date_time_bookElement);
	        
	        if (booking.getId_show() > 0) {
	            Element show_idElement = new Element("id_show", XML_NS);
	            show_idElement.setText(Long.toString(booking.getId_show()));
	            bookingElement.addContent(show_idElement);
	        }
	        
	        Element discounted_priceElement = new Element("discounted_price", XML_NS);
	        discounted_priceElement.setText(Float.toString(booking.getDiscounted_price()));
	        bookingElement.addContent(discounted_priceElement);
	        
	        Element usedElement = new Element("used", XML_NS);
	        usedElement.setText(Boolean.toString(booking.isUsed()));
	        bookingElement.addContent(usedElement);
	        
	        Element num_ticketsElement = new Element("num_tickets", XML_NS);
	        num_ticketsElement.setText(Integer.toString(booking.getNum_tickets()));
	        bookingElement.addContent(num_ticketsElement);
	        
	        
	        return bookingElement;   
	        
	     }    
	    
	    
	    public static ClientBookingDto toClientBookingDto(InputStream bookingXml)
	            throws ParsingException {
	        try {

	            SAXBuilder builder = new SAXBuilder();
	            Document document = builder.build(bookingXml);
	            Element rootElement = document.getRootElement();

	            return toClientBookingDto(rootElement);
	        } catch (ParsingException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new ParsingException(e);
	        }
	    }

	    public static List<ClientBookingDto> toClientBookingDtos(InputStream bookingXml)
	            throws ParsingException {
	        try {

	            SAXBuilder builder = new SAXBuilder();
	            Document document = builder.build(bookingXml);
	            Element rootElement = document.getRootElement();

	            if (!"booking".equalsIgnoreCase(rootElement.getName())) {
	                throw new ParsingException("Unrecognized element '"
	                        + rootElement.getName() + "' ('booking' expected)");
	            }
	            List<Element> children = rootElement.getChildren();
	            List<ClientBookingDto> bookingDtos = new ArrayList<>(children.size());
	            for (int i = 0; i < children.size(); i++) {
	                Element element = children.get(i);
	                bookingDtos.add(toClientBookingDto(element));
	            }

	            return bookingDtos;
	        } catch (ParsingException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new ParsingException(e);
	        }
	    }

	    private static ClientBookingDto toClientBookingDto(Element bookingElement)
	            throws ParsingException, DataConversionException {
	    	if (!"booking".equals(bookingElement.getName())) {
	            throw new ParsingException("Unrecognized element '"
	                    + bookingElement.getName() + "' ('booking' expected)");
	        }
	        Element Id_booking = bookingElement.getChild("id_booking", XML_NS);
	        Long identifier = null;
	        if (Id_booking != null) {
	        	identifier = Long.valueOf(Id_booking.getTextTrim());
	        }

	        Element id_show_element = bookingElement.getChild("id_show", XML_NS);
	        Long id_show = null;
	        if (id_show_element != null) {
	        	id_show = Long.valueOf(id_show_element.getTextTrim());
	        }

	        String email = bookingElement.getChildTextTrim("email", XML_NS);
	        
	        String credit_card = bookingElement.getChildTextTrim("creditcardnumber", XML_NS);
	        
	        LocalDateTime date_time_book = LocalDateTime.parse(bookingElement.getChildTextTrim("date_time_book", XML_NS));
	        
	        float discounted_price = Float.valueOf(bookingElement.getChildTextTrim("discounted_price", XML_NS));
	        
	        Boolean used = Boolean.valueOf(bookingElement.getChildTextTrim("used", XML_NS));
	        
	        Integer num_tickets = Integer.valueOf(bookingElement.getChildTextTrim("num_tickets", XML_NS));
	        
	        return new ClientBookingDto(identifier,email,credit_card,date_time_book,id_show,discounted_price,used,num_tickets);
	    }


}