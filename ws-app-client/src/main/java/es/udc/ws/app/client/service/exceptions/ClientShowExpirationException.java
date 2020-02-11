package es.udc.ws.app.client.service.exceptions;

import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class ClientShowExpirationException extends Exception {
	private long id_show;
    private LocalDateTime expirationDate;

    public ClientShowExpirationException(long id_show,LocalDateTime expirationDate) {
        super("Show with id=\"" + id_show + 
              "\" has expired (expirationDate = \"" + 
              expirationDate + "\")");
        this.id_show = id_show;
        this.expirationDate = expirationDate;
    }

	public long getId_show() {
		return id_show;
	}

	public void setId_show(long id_show) {
		this.id_show = id_show;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

}
