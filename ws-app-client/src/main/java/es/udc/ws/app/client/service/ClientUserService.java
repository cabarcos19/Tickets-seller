package es.udc.ws.app.client.service;

import java.util.List;

import es.udc.ws.app.client.service.dto.ClientBookingDto;
import es.udc.ws.app.client.service.dto.ClientUserFindDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.client.service.exceptions.*;

public interface ClientUserService {
	public List<ClientUserFindDto> findKey(String Key);
	public List<ClientBookingDto> findEmail(String email ) throws InputValidationException;
	public ClientBookingDto book(ClientBookingDto booking)throws InputValidationException, InstanceNotFoundException, ClientSoldOutTicketsException, ClientShowExpirationException;
    
}
