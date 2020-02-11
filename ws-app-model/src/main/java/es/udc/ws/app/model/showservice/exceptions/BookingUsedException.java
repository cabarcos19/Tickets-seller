package es.udc.ws.app.model.showservice.exceptions;

@SuppressWarnings("serial")
public class BookingUsedException extends Exception{
	private long id_booking;

	public BookingUsedException(long id_booking) {
        super("Booking with id=" + id_booking + "has been used");
		
        this.id_booking = id_booking;
	}

	public long getId_booking() {
		return id_booking;
	}

	public void setId_booking(long id_booking) {
		this.id_booking = id_booking;
	}
	
}
