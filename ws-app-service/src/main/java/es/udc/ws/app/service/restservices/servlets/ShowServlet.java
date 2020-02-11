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

import es.udc.ws.app.service.dto.ServiceShowDto;
import es.udc.ws.app.model.show.Show;
import es.udc.ws.app.model.showservice.ShowServiceFactory;
import es.udc.ws.app.service.restservice.xml.XmlServiceExceptionConversor;
import es.udc.ws.app.service.restservice.xml.XmlServiceShowDtoConversor;
import es.udc.ws.app.service.serviceutil.ShowToShowDtoConversor;
import es.udc.ws.util.exceptions.*;
import es.udc.ws.app.model.showservice.exceptions.*;
import es.udc.ws.util.servlet.ServletUtils;
import es.udc.ws.util.xml.exceptions.ParsingException;

@SuppressWarnings("serial")
public class ShowServlet extends HttpServlet {

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
    	ServiceShowDto xmlshow;
        try {
        	xmlshow = XmlServiceShowDtoConversor.toServiceShowDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, XmlServiceExceptionConversor
                    .toInputValidationExceptionXml(new InputValidationException(ex.getMessage())), null);

            return;

        }
        Show show = ShowToShowDtoConversor.toShow(xmlshow);
        try {
        	show = ShowServiceFactory.getService().createShow(show);
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(ex), null);
            return;
        }
        ServiceShowDto showDto = ShowToShowDtoConversor.toShowDto(show);

        String showURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + show.getId_show();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", showURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                XmlServiceShowDtoConversor.toXml(showDto), headers);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
                            new InputValidationException("Invalid Request: " + "invalid show id")),
                    null);
            return;
        }
        String showIdAsString = path.substring(1);
        Long id_show;
        try {
        	id_show = Long.valueOf(showIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
                            "Invalid Request: " + "invalid show id '" + showIdAsString + "'")),
                    null);
            return;
        }

        ServiceShowDto showDto;
        try {
            showDto = XmlServiceShowDtoConversor.toServiceShowDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, XmlServiceExceptionConversor
                    .toInputValidationExceptionXml(new InputValidationException(ex.getMessage())), null);
            return;

        }
        if (!id_show.equals(showDto.getId_show())) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
                            new InputValidationException("Invalid Request: " + "invalid show id")),
                    null);
            return;
        }
        Show show = ShowToShowDtoConversor.toShow(showDto);
        try {
            ShowServiceFactory.getService().updateShow(show);
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(ex), null);
            return;
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    XmlServiceExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        }catch (MaxTicketsException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_EXPECTATION_FAILED,
                    XmlServiceExceptionConversor.toMaxTicketsException(ex), null);
            return;
        } catch (DiscountedPriceException ex) {
        	ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE,
                    XmlServiceExceptionConversor.toDiscountedPriceException(ex), null);
		}
        
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            String keyWords = req.getParameter("keywords");
            List<Show> shows = new ArrayList<>();
			try {
				shows = ShowServiceFactory.getService().findKey(keyWords, null, null);
			} catch (InputValidationException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
            List<ServiceShowDto> showDtos = ShowToShowDtoConversor.toShowDtos(shows);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    XmlServiceShowDtoConversor.toXml(showDtos), null);
        } else {
            String showIdAsString = path.substring(1);
            Long id_show;
            try {
                id_show = Long.valueOf(showIdAsString);
            } catch (NumberFormatException ex) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        XmlServiceExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
                                "Invalid Request: " + "invalid show id'" + showIdAsString + "'")),
                        null);

                return;
            }
            Show show;
            try {
                show = ShowServiceFactory.getService().findShow(id_show);
            } catch (InstanceNotFoundException ex) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                        XmlServiceExceptionConversor.toInstanceNotFoundException(ex), null);
                return;
            }
            ServiceShowDto showDto = ShowToShowDtoConversor.toShowDto(show);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    XmlServiceShowDtoConversor.toXml(showDto), null);
        }
    }

}
