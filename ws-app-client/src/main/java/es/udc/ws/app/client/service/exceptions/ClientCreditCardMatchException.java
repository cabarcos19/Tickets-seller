package es.udc.ws.app.client.service.exceptions;

@SuppressWarnings("serial")
public class ClientCreditCardMatchException extends Exception{
	private long id_booking;

	public ClientCreditCardMatchException(Long id_booking) {
        super("Credit cards doesn't match for booking= " + id_booking);
		
	}

	public long getId_booking() {
		return id_booking;
	}

	public void setId_booking(long id_booking) {
		this.id_booking = id_booking;
	}
	
}