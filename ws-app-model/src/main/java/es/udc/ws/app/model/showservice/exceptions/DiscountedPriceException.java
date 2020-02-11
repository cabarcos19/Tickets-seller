package es.udc.ws.app.model.showservice.exceptions;

@SuppressWarnings("serial")
public class DiscountedPriceException extends Exception{
	private long id_show;

	public DiscountedPriceException(long id_show) {
        super("Can't reduce discounted price in show= "+id_show);
		
        this.id_show = id_show;
	}

	public long getId_show() {
		return id_show;
	}

	public void setId_show(long id_show) {
		this.id_show = id_show;
	}
}
