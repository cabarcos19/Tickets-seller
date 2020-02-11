package es.udc.ws.app.client.service.rest.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.app.client.service.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.xml.exceptions.ParsingException;

public class XmlClientExceptionConversor {

    public final static String CONVERSION_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z";

    public final static Namespace XML_NS = 
            Namespace.getNamespace("http://ws.udc.es/shows/xml");
    public final static Namespace XML_NSB = Namespace
            .getNamespace("http://ws.udc.es/bookings/xml");

    public static InputValidationException fromInputValidationExceptionXml(InputStream ex) 
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element message = rootElement.getChild("message", XML_NS);

            return new InputValidationException(message.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    
    public static ClientBookingUsedException fromBookingUsedExceptionXml(InputStream ex) 
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element message = rootElement.getChild("id_booking", XML_NS);

            return new ClientBookingUsedException(Long.parseLong(message.getText()));
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static InstanceNotFoundException fromInstanceNotFoundExceptionXml(InputStream ex) 
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("instanceId", XML_NS);
            Element instanceType = rootElement.getChild("instanceType", XML_NS);

            return new InstanceNotFoundException(instanceId.getText(), instanceType.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static ClientShowExpirationException fromSaleExpirationExceptionXml(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("id_show", XML_NS);
            Element expirationDate = rootElement.getChild("expirationDate", XML_NS);

            LocalDateTime calendar = null;
            if (expirationDate != null) {
                calendar.parse(expirationDate.getText());
            }

            return new ClientShowExpirationException(Long.parseLong(instanceId.getTextTrim()),calendar);
        } catch (JDOMException | IOException | NumberFormatException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    
    public static ClientSoldOutTicketsException fromSoldOutTicketsExceptionXml(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("id_booking", XML_NSB);
            Element num_tickets = rootElement.getChild("tickets", XML_NSB);
            Long.parseLong(instanceId.getTextTrim());

            return new ClientSoldOutTicketsException(Long.parseLong(instanceId.getTextTrim()),Integer.parseInt(num_tickets.getTextTrim()));
        } catch (JDOMException | IOException | NumberFormatException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    
    public static ClientCreditCardMatchException fromCreditCardMatchException(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("id_booking", XML_NSB);
            Long.parseLong(instanceId.getTextTrim());

            return new ClientCreditCardMatchException(Long.parseLong(instanceId.getTextTrim()));
        } catch (JDOMException | IOException | NumberFormatException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    
    public static ClientMaxTicketsException fromMaxTicketsException(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("id_show", XML_NS);
            Long.parseLong(instanceId.getTextTrim());

            return new ClientMaxTicketsException(Long.parseLong(instanceId.getTextTrim()));
        } catch (JDOMException | IOException | NumberFormatException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    
    public static ClientDiscountedPriceException fromDiscountedPriceException(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("id_show", XML_NS);
            Long.parseLong(instanceId.getTextTrim());

            return new ClientDiscountedPriceException(Long.parseLong(instanceId.getTextTrim()));
        } catch (JDOMException | IOException | NumberFormatException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    
    

}
