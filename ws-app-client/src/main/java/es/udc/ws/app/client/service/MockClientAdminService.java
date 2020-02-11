package es.udc.ws.app.client.service;

import java.util.List;

import es.udc.ws.app.client.service.dto.ClientAdminShowDto;
import es.udc.ws.app.client.service.dto.ClientBookingDto;
import es.udc.ws.app.client.service.exceptions.ClientBookingUsedException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class MockClientAdminService implements ClientAdminService{

	@Override
	public Long createShow(ClientAdminShowDto e) throws InputValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateShow(ClientAdminShowDto show) throws InputValidationException, InstanceNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClientAdminShowDto findShow(Long id_show) throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void redeeemTicket(long id_booking, String credit_card)
			throws InputValidationException, InstanceNotFoundException, ClientBookingUsedException {
		// TODO Auto-generated method stub
		
	}
	
	
}
