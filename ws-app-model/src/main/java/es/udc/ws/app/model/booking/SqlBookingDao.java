package es.udc.ws.app.model.booking;

import java.sql.Connection;
import java.util.List;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlBookingDao {
	public Booking create (Connection c,Booking b);
	public Booking find (Connection c, Long id_booking) throws InstanceNotFoundException;
	public void update (Connection c, Booking b) throws InstanceNotFoundException;
	public void remove (Connection c, Long id_booking) throws InstanceNotFoundException;
	public List<Booking> findByUser(Connection c,String email);
	public Integer getShowBookings(Connection c, Long id_show);
}
