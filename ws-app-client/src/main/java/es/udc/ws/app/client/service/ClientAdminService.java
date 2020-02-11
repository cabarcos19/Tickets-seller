package es.udc.ws.app.client.service;


import es.udc.ws.app.client.service.dto.ClientAdminShowDto;
import es.udc.ws.app.client.service.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface ClientAdminService {

	public Long createShow(ClientAdminShowDto e) throws InputValidationException;
	public void updateShow(ClientAdminShowDto show) throws InputValidationException, InstanceNotFoundException, ClientMaxTicketsException, ClientDiscountedPriceException;
	public ClientAdminShowDto findShow(Long id_show) throws InstanceNotFoundException;
	public void	redeeemTicket(long id_booking,String credit_card)throws InputValidationException, InstanceNotFoundException,ClientBookingUsedException, ClientCreditCardMatchException ;
}
