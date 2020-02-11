package es.udc.ws.app.model.show;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;




public class Jdbc3CcSqlShowDao extends AbstractSqlShowDao {
	
	@Override
    public Show create(Connection c, Show show) {

        /* Create "queryString". */
        String queryString = "INSERT INTO Shows"
                + " (name, description, duration, date_time_lim, max_tickets, real_price, discounted_price,"
                + " commission_sale, remaining_tickets, date_time_show)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (PreparedStatement preparedStatement = c.prepareStatement(
                        queryString, Statement.RETURN_GENERATED_KEYS)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, show.getName());
            preparedStatement.setString(i++, show.getDescription());
            preparedStatement.setInt(i++, show.getDuration());
            preparedStatement.setTimestamp(i++,Timestamp.valueOf(show.getDate_time_lim()));
            preparedStatement.setInt(i++, show.getMax_tickets());
            preparedStatement.setFloat(i++, show.getReal_price());
            preparedStatement.setFloat(i++, show.getDiscounted_price());    
            preparedStatement.setDouble(i++, show.getCommission_sale());
            preparedStatement.setInt(i++, show.getRemaining_tickets());
            preparedStatement.setTimestamp(i++,Timestamp.valueOf(show.getDate_time_show()));
            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long id_show = resultSet.getLong(1);

            /* Return show. */
            return new Show(id_show, show.getName(),show.getDescription(),
                    show.getDuration(),show.getDate_time_lim(),show.getMax_tickets(),
                    show.getReal_price(),show.getDiscounted_price(),show.getCommission_sale(),
                    show.getRemaining_tickets(),show.getDate_time_show());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
	

}
