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

import es.udc.ws.app.client.service.dto.ClientUserFindDto;
import es.udc.ws.util.xml.exceptions.ParsingException;

public class XmlClientUserFindDtoConversor {
	 public final static Namespace XML_NS = 
	            Namespace.getNamespace("http://ws.udc.es/find/xml");

	    public static Document toXml(ClientUserFindDto show)
	            throws IOException {

	        Element showElement = toJDOMElement(show);

	        return new Document(showElement);
	    }

	    public static Element toJDOMElement(ClientUserFindDto show) {

	        Element showElement = new Element("show", XML_NS);

	        if (show.getId_show() != null) {
	            Element identifierElement = new Element("showId", XML_NS);
	            identifierElement.setText(show.getId_show().toString());
	            showElement.addContent(identifierElement);
	        }

	        Element nameElement = new Element("name", XML_NS);
	        nameElement.setText(show.getName());
	        showElement.addContent(nameElement);  

	        Element descriptionElement = new Element("description", XML_NS);
	        descriptionElement.setText(show.getDescription());
	        showElement.addContent(descriptionElement);
	        
	        Element date_time_showElement = new Element("date_time_show", XML_NS);
	        date_time_showElement.setText(show.getDate_time_show().toString());
	        showElement.addContent(date_time_showElement);

	        Element date_time_show_finishElement = new Element("date_time_show_finish", XML_NS);
	        date_time_show_finishElement.setText(show.getDate_time_show_finish().toString());
	        showElement.addContent(date_time_show_finishElement);
	        
	        Element date_time_limElement = new Element("date_time_lim", XML_NS);
	        date_time_limElement.setText(show.getDate_time_lim().toString());
	        showElement.addContent(date_time_limElement);
	        
	        Element max_tickets_Element = new Element("max_tickets", XML_NS);
	        max_tickets_Element.setText(Integer.toString(show.getMax_tickets()));
	        showElement.addContent(max_tickets_Element);
	        
	        Element real_priceElement = new Element("real_price", XML_NS);
	        real_priceElement.setText(Float.toString(show.getReal_price()));
	        showElement.addContent(real_priceElement);

	        Element discounted_priceElement = new Element("discounted_price", XML_NS);
	        discounted_priceElement.setText(Float.toString(show.getDiscounted_price()));
	        showElement.addContent(discounted_priceElement);

	        return showElement;
	    }

	    public static ClientUserFindDto toClientShowDto(InputStream showXml)
	            throws ParsingException {
	        try {

	            SAXBuilder builder = new SAXBuilder();
	            Document document = builder.build(showXml);
	            Element rootElement = document.getRootElement();

	            return toClientShowDto(rootElement);
	        } catch (ParsingException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new ParsingException(e);
	        }
	    }

	    public static List<ClientUserFindDto> toClientShowDtos(InputStream showXml)
	            throws ParsingException {
	        try {

	            SAXBuilder builder = new SAXBuilder();
	            Document document = builder.build(showXml);
	            Element rootElement = document.getRootElement();

	            if (!"find".equalsIgnoreCase(rootElement.getName())) {
	                throw new ParsingException("Unrecognized element '"
	                        + rootElement.getName() + "' ('show' expected)");
	            }
	            List<Element> children = rootElement.getChildren();
	            List<ClientUserFindDto> showDtos = new ArrayList<>(children.size());
	            for (int i = 0; i < children.size(); i++) {
	                Element element = children.get(i);
	                showDtos.add(toClientShowDto(element));
	            }

	            return showDtos;
	        } catch (ParsingException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new ParsingException(e);
	        }
	    }

	    private static ClientUserFindDto toClientShowDto(Element showElement)
	            throws ParsingException, DataConversionException {
	        if (!"show".equals(
	                showElement.getName())) {
	            throw new ParsingException("Unrecognized element '"
	                    + showElement.getName() + "' ('show' expected)");
	        }
	        Element identifierElement = showElement.getChild("id_show", XML_NS);
	        Long identifier = null;

	        if (identifierElement != null) {
	            identifier = Long.valueOf(identifierElement.getTextTrim());
	        }

	        String description = showElement.getChildTextNormalize("description", XML_NS);

	        String name = showElement.getChildTextNormalize("name", XML_NS);

	        int max_tickets = Integer.valueOf(showElement.getChildTextTrim("max_tickets", XML_NS));
	        
	        int duration = Integer.valueOf(showElement.getChildTextTrim("duration", XML_NS));
	        
	        float real_price = Float.valueOf(showElement.getChildTextTrim("real_price", XML_NS));
	        
	        LocalDateTime date_time_lim = LocalDateTime.parse(showElement.getChildTextTrim("date_time_lim", XML_NS));
	        
	        float discounted_price = Float.valueOf(showElement.getChildTextTrim("discounted_price", XML_NS));
	        
	        LocalDateTime date_time_show = LocalDateTime.parse(showElement.getChildTextTrim("date_time_show", XML_NS));
	        
	        LocalDateTime date_time_show_finish=date_time_show.plusMinutes(duration);

	        return new ClientUserFindDto(identifier, name, description, date_time_show_finish,date_time_show ,date_time_lim,max_tickets,real_price,discounted_price);
	    }
}
