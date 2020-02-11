package es.udc.ws.app.service.restservice.xml;
 
import java.io.IOException;
import java.util.List;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import es.udc.ws.app.service.dto.ServiceBookingDto;

 
public class XmlServiceBookingDtoConversor {
 
    public final static Namespace XML_NS = Namespace
            .getNamespace("http://ws.udc.es/bookings/xml");
 
    public static Document toResponse(List<ServiceBookingDto> bookings) throws IOException {

        Element bookingsElement = new Element("booking", XML_NS);
        for (int i = 0; i < bookings.size(); i++) {
        	ServiceBookingDto xmlbookingDto = bookings.get(i);
            Element bookingElement = toXml(xmlbookingDto);
            bookingsElement.addContent(bookingElement);
        }

        return new Document(bookingsElement);
    }
    
    public static Document toResponse(ServiceBookingDto booking) throws IOException {

        Element bookingElement = new Element("booking", XML_NS);
        bookingElement = toXml(booking);
        return new Document(bookingElement);
    }
 
    public static Element toXml(ServiceBookingDto booking) {
 
        Element bookingElement = new Element("booking", XML_NS);
         
        if (booking.getId_booking() > 0) {
            Element bookingIdElement = new Element("id_booking", XML_NS);
            bookingIdElement.setText(Long.toString(booking.getId_booking()));
            bookingElement.addContent(bookingIdElement);
        }
        
            Element bookingEmailElement = new Element("email", XML_NS);
            bookingEmailElement.setText(booking.getEmail());
            bookingElement.addContent(bookingEmailElement);
            
            Element bookingCardElement = new Element("creditcardnumber", XML_NS);
            bookingCardElement.setText(booking.getCredit_card());
            bookingElement.addContent(bookingCardElement);
            
            Element bookingDateElement = new Element("date_time_book", XML_NS);
            bookingDateElement.setText(booking.getDate_time_book().toString());
            bookingElement.addContent(bookingDateElement);
            
            Element bookingDiscountedElement = new Element("discounted_price", XML_NS);
            bookingDiscountedElement.setText(Float.toString(booking.getDiscounted_price()));
            bookingElement.addContent(bookingDiscountedElement);
            
            Element bookingusedElement = new Element("used", XML_NS);
            bookingusedElement.setText(Boolean.toString(booking.isUsed()));
            bookingElement.addContent(bookingusedElement);
            
            Element bookingticketsElement = new Element("num_tickets", XML_NS);
            bookingticketsElement.setText(Integer.toString(booking.getNum_tickets()));
            bookingElement.addContent(bookingticketsElement);

            Element showIdElement = new Element("id_show", XML_NS);
            showIdElement.setText(Long.toString(booking.getId_show()));
            bookingElement.addContent(showIdElement);
            
        return bookingElement;
    }
 
}