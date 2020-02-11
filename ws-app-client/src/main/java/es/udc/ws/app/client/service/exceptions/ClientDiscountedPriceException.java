package es.udc.ws.app.client.service.exceptions;

@SuppressWarnings("serial")
public class ClientDiscountedPriceException extends Exception{
	private long id_show;

	public ClientDiscountedPriceException(long id_show) {
        super("Can't reduce discounted_price for show= "+id_show);
		this.id_show = id_show;
	}

	public long getId_show() {
		return id_show;
	}

	public void setId_show(long id_show) {
		this.id_show = id_show;
	}
}