package es.udc.ws.app.model.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jdbc3CcSqlBookingDao extends AbstractSqlBookingDao{
	@Override
    public Booking create(Connection c, Booking booking) {

        /* Create "queryString". */
        String queryString = "INSERT INTO Booking"
                + " (email,credit_card,date_time_book,id_show,discounted_price,used,num_tickets)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = c.prepareStatement(
                        queryString, Statement.RETURN_GENERATED_KEYS)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, booking.getEmail());
            preparedStatement.setString(i++, booking.getCredit_card());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(booking.getDate_time_book()));
            preparedStatement.setLong(i++, booking.getId_show());
            preparedStatement.setFloat(i++, booking.getDiscounted_price());
            preparedStatement.setBoolean(i++, booking.isUsed());
            preparedStatement.setInt(i++, booking.getNum_tickets());
            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long id_booking = resultSet.getLong(1);

            /* Return booking. */
            return new Booking(id_booking,booking.getEmail(),booking.getCredit_card(),booking.getDate_time_book(),booking.getId_show(),booking.getDiscounted_price(),booking.isUsed(),booking.getNum_tickets());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
