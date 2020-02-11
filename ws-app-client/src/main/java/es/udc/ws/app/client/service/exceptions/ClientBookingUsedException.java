package es.udc.ws.app.client.service.exceptions;

@SuppressWarnings("serial")
public class ClientBookingUsedException extends Exception {
	private long id_booking;

	public ClientBookingUsedException(long id_booking) {
        super("Booking with id= " + id_booking + " has been used");
		
        this.id_booking = id_booking;
	}

	public long getId_booking() {
		return id_booking;
	}

	public void setId_booking(long id_booking) {
		this.id_booking = id_booking;
	}
}
