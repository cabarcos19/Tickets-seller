package es.udc.ws.app.service.restservice.xml;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import es.udc.ws.app.model.showservice.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class XmlServiceExceptionConversor {

    public final static String CONVERSION_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z";

    public final static Namespace XML_NS = Namespace.getNamespace("http://ws.udc.es/shows/xml");
    
    public final static Namespace XML_NSB = Namespace.getNamespace("http://ws.udc.es/bookings/xml");

    public static Document toInputValidationExceptionXml(InputValidationException ex) throws IOException {

        Element exceptionElement = new Element("InputValidationException", XML_NS);

        Element messageElement = new Element("message", XML_NS);
        messageElement.setText(ex.getMessage());
        exceptionElement.addContent(messageElement);

        return new Document(exceptionElement);
    }

    public static Document toInstanceNotFoundException(InstanceNotFoundException ex) throws IOException {

        Element exceptionElement = new Element("InstanceNotFoundException", XML_NS);

        if (ex.getInstanceId() != null) {
            Element instanceIdElement = new Element("instanceId", XML_NS);
            instanceIdElement.setText(ex.getInstanceId().toString());

            exceptionElement.addContent(instanceIdElement);
        }

        if (ex.getInstanceType() != null) {
            Element instanceTypeElement = new Element("instanceType", XML_NS);
            instanceTypeElement.setText(ex.getInstanceType());

            exceptionElement.addContent(instanceTypeElement);
        }
        return new Document(exceptionElement);
    }
    
    public static Document toBookingUsedException(BookingUsedException ex) throws IOException {

        Element exceptionElement = new Element("BookingUsedException", XML_NS);

        if (ex.getId_booking()>0) {
            Element instanceIdElement = new Element("id_booking", XML_NS);
            instanceIdElement.setText(Long.toString(ex.getId_booking()));

            exceptionElement.addContent(instanceIdElement);
        }
        return new Document(exceptionElement);
    }

    public static Document toShowExpirationException(ShowExpirationException ex) throws IOException {

        Element exceptionElement = new Element("ShowExpirationException", XML_NSB);

        if (ex.getId_show()>0) {
            Element bookingIdElement = new Element("id_booking", XML_NSB);
            bookingIdElement.setText(Long.toString(ex.getId_show()));
            exceptionElement.addContent(bookingIdElement);
        }

        if (ex.getExpirationDate() != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(CONVERSION_PATTERN, Locale.ENGLISH);

            Element expirationDateElement = new Element("expirationDate", XML_NSB);
            expirationDateElement.setText(dateFormatter.format(ex.getExpirationDate()));

            exceptionElement.addContent(expirationDateElement);
        }

        return new Document(exceptionElement);
    }

	public static Document toSoldOutTicketsException(SoldOutTicketsException ex) {
		Element exceptionElement = new Element("ShowExpirationException", XML_NSB);

        if (ex.getId_show()>0) {
            Element bookingIdElement = new Element("id_booking", XML_NSB);
            bookingIdElement.setText(Long.toString(ex.getId_show()));
            exceptionElement.addContent(bookingIdElement);
        }

        if (ex.getTickets() != 0) {
            Element soldoutelement = new Element("tickets", XML_NSB);
            soldoutelement.setText(Integer.toString(ex.getTickets()));

            exceptionElement.addContent(soldoutelement);
        }

        return new Document(exceptionElement);
    }
	
	public static Document toMaxTicketsException(MaxTicketsException ex) {
		Element exceptionElement = new Element("MaxTicketsException", XML_NS);

        if (ex.getId_show()>0) {
            Element bookingIdElement = new Element("id_show", XML_NS);
            bookingIdElement.setText(Long.toString(ex.getId_show()));
            exceptionElement.addContent(bookingIdElement);
        }

        return new Document(exceptionElement);
    }
	
	public static Document toDiscountedPriceException(DiscountedPriceException ex) {
		Element exceptionElement = new Element("DiscountedPriceException", XML_NS);

        if (ex.getId_show()>0) {
            Element bookingIdElement = new Element("id_show", XML_NS);
            bookingIdElement.setText(Long.toString(ex.getId_show()));
            exceptionElement.addContent(bookingIdElement);
        }

        return new Document(exceptionElement);
    }
	
	public static Document toCreditCardMatchException(CreditCardMatchException ex) {

        Element exceptionElement = new Element("CreditCardMatchException", XML_NSB);

        if (ex.getId_booking()>0) {
            Element instanceIdElement = new Element("id_booking", XML_NSB);
            instanceIdElement.setText(Long.toString(ex.getId_booking()));

            exceptionElement.addContent(instanceIdElement);
        }
        return new Document(exceptionElement);
    }
}
