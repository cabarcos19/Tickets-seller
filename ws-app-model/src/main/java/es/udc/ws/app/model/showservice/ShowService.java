package es.udc.ws.app.model.showservice;

import java.time.LocalDateTime;
import java.util.List;

import es.udc.ws.app.model.show.Show;
import es.udc.ws.app.model.showservice.exceptions.BookingUsedException;
import es.udc.ws.app.model.showservice.exceptions.CreditCardMatchException;
import es.udc.ws.app.model.showservice.exceptions.DiscountedPriceException;
import es.udc.ws.app.model.showservice.exceptions.MaxTicketsException;
import es.udc.ws.app.model.showservice.exceptions.ShowExpirationException;
import es.udc.ws.app.model.showservice.exceptions.SoldOutTicketsException;
import es.udc.ws.app.model.booking.Booking;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface ShowService {
	public Show createShow(Show e) throws InputValidationException;
	public void updateShow(Show show) throws InputValidationException, InstanceNotFoundException, DiscountedPriceException, MaxTicketsException;
	public Show findShow(Long id_show) throws InstanceNotFoundException;
	public List<Show> findKey(String key,LocalDateTime fecha_inicio,LocalDateTime fecha_fin) throws InputValidationException;
	public long booking(long id_show, String email, String credit_card, int ticket)throws InstanceNotFoundException,InputValidationException,ShowExpirationException,SoldOutTicketsException;
	public List<Booking>findBookings(String email)throws InputValidationException;
    public void	redeeemTicket(long id_booking,String credit_card)throws InputValidationException, InstanceNotFoundException,BookingUsedException, CreditCardMatchException;
}
