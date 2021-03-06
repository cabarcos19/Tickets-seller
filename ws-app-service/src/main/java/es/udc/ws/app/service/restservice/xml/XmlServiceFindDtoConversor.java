package es.udc.ws.app.service.restservice.xml;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.app.service.dto.ServiceFindDto;
import es.udc.ws.util.xml.exceptions.ParsingException;

public class XmlServiceFindDtoConversor {
    public final static Namespace XML_NS = Namespace.getNamespace("http://ws.udc.es/find/xml");

    public static Document toXml(ServiceFindDto find) throws IOException {

        Element showElement = toJDOMElement(find);

        return new Document(showElement);
    }

    public static Document toXml(List<ServiceFindDto> find) throws IOException {

        Element showsElement = new Element("find", XML_NS);
        for (int i = 0; i < find.size(); i++) {
        	ServiceFindDto xmlFindDto = find.get(i);
            Element showElement = toJDOMElement(xmlFindDto);
            showsElement.addContent(showElement);
        }

        return new Document(showsElement);
    }

    public static Element toJDOMElement(ServiceFindDto find) {

        Element showElement = new Element("show", XML_NS);

        if (find.getId_show() != null) {
            Element identifierElement = new Element("id_show", XML_NS);
            identifierElement.setText(find.getId_show().toString());
            showElement.addContent(identifierElement);
        }

        Element nameElement = new Element("name", XML_NS);
        nameElement.setText(find.getName());
        showElement.addContent(nameElement);  

        Element descriptionElement = new Element("description", XML_NS);
        descriptionElement.setText(find.getDescription());
        showElement.addContent(descriptionElement);
        
        Element date_time_showElement = new Element("date_time_show", XML_NS);
        date_time_showElement.setText(find.getDate_time_show().toString());
        showElement.addContent(date_time_showElement);

        Element durationElement = new Element("duration", XML_NS);
        durationElement.setText(Integer.toString(find.getDuration()));
        showElement.addContent(durationElement);
        
        Element date_time_limElement = new Element("date_time_lim", XML_NS);
        date_time_limElement.setText(find.getDate_time_lim().toString());
        showElement.addContent(date_time_limElement);
        
        Element max_tickets_Element = new Element("max_tickets", XML_NS);
        max_tickets_Element.setText(Integer.toString(find.getMax_tickets()));
        showElement.addContent(max_tickets_Element);
        
        Element real_priceElement = new Element("real_price", XML_NS);
        real_priceElement.setText(Float.toString(find.getReal_price()));
        showElement.addContent(real_priceElement);

        Element discounted_priceElement = new Element("discounted_price", XML_NS);
        discounted_priceElement.setText(Float.toString(find.getDiscounted_price()));
        showElement.addContent(discounted_priceElement);


        return showElement;
    }

    public static ServiceFindDto toServiceFindDto(InputStream findXml) throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(findXml);
            Element rootElement = document.getRootElement();

            return toServiceFindDto(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ServiceFindDto toServiceFindDto(Element showElement)
            throws ParsingException, DataConversionException {
        if (!"show".equals(showElement.getName())) {
            throw new ParsingException("Unrecognized element '" + showElement.getName() + "' ('show' expected)");
        }
        Element identifierElement = showElement.getChild("id_show", XML_NS);
        Long identifier = null;

        if (identifierElement != null) {
            identifier = Long.valueOf(identifierElement.getTextTrim());
        }

        String description = showElement.getChildTextNormalize("description", XML_NS);

        String name = showElement.getChildTextNormalize("name", XML_NS);

        short duration = Short.valueOf(showElement.getChildTextTrim("duration", XML_NS));

        float real_price = Float.valueOf(showElement.getChildTextTrim("real_price", XML_NS));
        
        LocalDateTime date_time_lim = LocalDateTime.parse(showElement.getChildTextTrim("date_time_lim", XML_NS));
        
        float discounted_price = Float.valueOf(showElement.getChildTextTrim("discounted_price", XML_NS));
        
        
        LocalDateTime date_time_show = LocalDateTime.parse(showElement.getChildTextTrim("date_time_show", XML_NS));
        
        int max_tickets = Integer.valueOf(showElement.getChildTextTrim("max_tickets", XML_NS));
        
        
        
        return new ServiceFindDto(identifier, name, description,duration, date_time_lim,max_tickets,real_price,discounted_price,date_time_show);
    }
	
	
	

}
