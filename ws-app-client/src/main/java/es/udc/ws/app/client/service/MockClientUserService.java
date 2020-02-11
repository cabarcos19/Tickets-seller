package es.udc.ws.app.client.service;

import java.util.List;

//import es.udc.ws.app.client.service.dto.ClientBookingDto;
import es.udc.ws.app.client.service.dto.ClientBookingDto;
import es.udc.ws.app.client.service.dto.ClientUserFindDto;
import es.udc.ws.app.client.service.exceptions.ClientShowExpirationException;
import es.udc.ws.app.client.service.exceptions.ClientSoldOutTicketsException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;


public class MockClientUserService implements ClientUserService{

	@Override
	public List<ClientUserFindDto> findKey(String Key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientBookingDto> findEmail(String email) throws InputValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientBookingDto book(ClientBookingDto booking) throws InputValidationException, InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	


}
