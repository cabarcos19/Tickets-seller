package es.udc.ws.app.model.showservice.exceptions;

@SuppressWarnings("serial")
public class SoldOutTicketsException extends Exception {

	private long id_show;
	private int tickets;

	public SoldOutTicketsException(long id_show,int tickets) {
        super("Show with id=\"" + id_show + 
                "\" has no this number of tickets (tickets = \"" + 
                tickets + "\")");
		this.id_show = id_show;
		this.tickets = tickets;
	}

	public long getId_show() {
		return id_show;
	}

	public void setId_show(long id_show) {
		this.id_show = id_show;
	}

	public int getTickets() {
		return tickets;
	}

	public void setTickets(int tickets) {
		this.tickets = tickets;
	}
	
	

}
