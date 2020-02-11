package es.udc.ws.app.model.showservice.exceptions;

@SuppressWarnings("serial")
public class CreditCardMatchException extends Exception{
	private long id_booking;

	public CreditCardMatchException(long id_booking) {
        super("Credit cards doesn't match for booking= " + id_booking);
        
        this.id_booking = id_booking;
		
	}

	public long getId_booking() {
		return id_booking;
	}

	public void setId_booking(long id_booking) {
		this.id_booking = id_booking;
	}
	
}
