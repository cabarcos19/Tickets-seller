package es.udc.ws.app.model.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.model.show.Show;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlBookingDao implements SqlBookingDao{

	protected AbstractSqlBookingDao() {
	}
	

	@Override
	public Booking find(Connection c, Long id_booking) throws InstanceNotFoundException {
		String queryString ="SELECT email,credit_card,date_time_book,id_show,discounted_price,used,num_tickets FROM Booking WHERE id_booking = ?";
		
		try (PreparedStatement preparedStatement = c.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, id_booking.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(id_booking,
                        Booking.class.getName());
            }
		
            /* Get results. */
            i = 1;
            
            String email = resultSet.getString(i++);
            String credit_card = resultSet.getString(i++);
            LocalDateTime date_time_book = resultSet.getTimestamp(i++).toLocalDateTime();
            long id_show = resultSet.getLong(i++);
            float discounted_price = resultSet.getFloat(i++);
            boolean used = resultSet.getBoolean(i++);
            int num_tickets = resultSet.getInt(i++);
            /* Return booking. */
            return new Booking(id_booking,email,credit_card,date_time_book,id_show,discounted_price,used,num_tickets);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public List<Booking> findByUser(Connection c,String email){
		String queryString ="SELECT id_booking,email,credit_card,date_time_book,id_show,discounted_price,used,num_tickets "
				+ "FROM Booking WHERE email = ?";
		List<Booking> bookings = new ArrayList<Booking>();
		try (PreparedStatement preparedStatement = c.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i,email);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            
            
            while (resultSet.next()) {
	            /* Get results. */
	            i = 1;
	            
	            long id_booking = resultSet.getLong(i++);
	            i++;
	            //email = resultSet.getString(i++);
	            String credit_card = resultSet.getString(i++);
	            LocalDateTime date_time_book = resultSet.getTimestamp(i++).toLocalDateTime();
	            long id_show = resultSet.getLong(i++);
	            float discounted_price = resultSet.getFloat(i++);
	            boolean used = resultSet.getBoolean(i++);
	            int num_tickets = resultSet.getInt(i++);
	            
	            bookings.add(new Booking(id_booking,email,credit_card,date_time_book,id_show,discounted_price,used,num_tickets));
            }    
            /* Return bookings list. */
            

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return bookings;
	}
	
	@Override
	public Integer getShowBookings(Connection c,Long id_show){
		int total_tickets=0;
		String queryString ="SELECT id_booking,email,credit_card,date_time_book,id_show,discounted_price,used,num_tickets "
				+ "FROM Booking WHERE id_show = ?";
		try (PreparedStatement preparedStatement = c.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i,Long.toString(id_show));

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            
            
            while (resultSet.next()) {
	            /* Get results. */
	            i = 1;
	            
	            i++;//id_booking
	            i++;//email
	            i++;//credit_card
	            i++;//date_time_book            
	            i++;//id_show
	            i++;//discounted_price
	            i++;//used
	            int num_tickets = resultSet.getInt(i++);
	            
	            total_tickets+=num_tickets;
            }    
            /* Return total tickets. */
            

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return total_tickets;
	}
	
	@Override
	public void remove(Connection c, Long id_booking) throws InstanceNotFoundException {

        /* Create "queryString". */
	
        String queryString = "DELETE FROM Booking WHERE" + " id_booking = ?";

        try (PreparedStatement preparedStatement = c.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
	
            int i = 1;
            preparedStatement.setLong(i++,id_booking);

            /* Execute query. */
	
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(id_booking,
                        Booking.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


	@Override
	public void update(Connection c, Booking b) throws InstanceNotFoundException {
		/* Create "queryString". */
        String queryString = "UPDATE Booking"
                + " SET email = ?,credit_card = ?,date_time_book = ?,id_show = ?,discounted_price = ?,"
                + "used = ?,num_tickets = ? WHERE id_booking = ?";

        try (PreparedStatement preparedStatement = c.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            
            int i = 1;
            preparedStatement.setString(i++,b.getEmail());
            preparedStatement.setString(i++, b.getCredit_card());
            preparedStatement.setTimestamp(i++,Timestamp.valueOf(b.getDate_time_book()));
            preparedStatement.setLong(i++,b.getId_show());
            preparedStatement.setFloat(i++, b.getDiscounted_price());    
            preparedStatement.setBoolean(i++,b.isUsed());
            preparedStatement.setInt(i++, b.getNum_tickets());
            preparedStatement.setLong(i++,b.getId_booking());
            

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(b.getId_booking(),
                        Show.class.getName());
            }
          
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

		
	}

	
	
}
