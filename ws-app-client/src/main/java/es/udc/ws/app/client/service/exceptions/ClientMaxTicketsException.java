package es.udc.ws.app.client.service.exceptions;

@SuppressWarnings("serial")
public class ClientMaxTicketsException extends Exception{
	private long id_show;

	public ClientMaxTicketsException(long id_show) {
        super("Can't reduce max_tickets for show= "+id_show+" (Less than bookings)");
		this.id_show = id_show;
	}

	public long getId_show() {
		return id_show;
	}

	public void setId_show(long id_show) {
		this.id_show = id_show;
	}
}