package es.udc.ws.app.service.soapservice;

public class SoapBookingUsedExceptionInfo {
	
	private Long id_booking;
	
    public SoapBookingUsedExceptionInfo() {
    }

	public SoapBookingUsedExceptionInfo(Long id_booking) {
		this.id_booking = id_booking;
	}

	public Long getId_booking() {
		return id_booking;
	}

	public void setId_booking(Long id_booking) {
		this.id_booking = id_booking;
	}    
}