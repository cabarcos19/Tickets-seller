package es.udc.ws.app.model.show;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.model.show.Show;


public abstract class AbstractSqlShowDao implements SqlShowDao {

	protected AbstractSqlShowDao() {
	}
	

	@Override
	public Show find(Connection c, Long id_show) throws InstanceNotFoundException {
		String queryString ="SELECT name,description,duration, date_time_lim,"
				+ "max_tickets,real_price,discounted_price,commission_sale,"
				+ "remaining_tickets, date_time_show FROM Shows WHERE id_show = ?";
		
		try (PreparedStatement preparedStatement = c.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, id_show.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(id_show,
                        Show.class.getName());
            }
		
            /* Get results. */
            i = 1;
            
            String name = resultSet.getString(i++);
            String description = resultSet.getString(i++);
            int duration = resultSet.getInt(i++);
            LocalDateTime date_time_lim = resultSet.getTimestamp(i++).toLocalDateTime();
            int max_tickets = resultSet.getInt(i++);
            float real_price = resultSet.getFloat(i++);
            float discounted_price = resultSet.getFloat(i++);
            double commission_sale = resultSet.getDouble(i++);
            int remaining_tickets = resultSet.getInt(i++);
            LocalDateTime date_time_show = resultSet.getTimestamp(i++).toLocalDateTime();
            
            /* Return movie. */
            return new Show(id_show, name,description,duration,date_time_lim,max_tickets,real_price,
            		discounted_price,commission_sale,remaining_tickets,date_time_show);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	//findShows-->findKey
	public List<Show> findShows(Connection c, String key, LocalDateTime ini, LocalDateTime fin) {
		/* Create "queryString". */
        String[] words = key != null ? key.split(" ") : null;
        String queryString;
    	queryString ="SELECT id_show,name,description, duration,"
				+ "date_time_lim,max_tickets,real_price,discounted_price,"
				+ "commission_sale,remaining_tickets,date_time_show FROM Shows";
        if (words != null && words.length > 0) {
            queryString += " WHERE";
            for (int i = 0; i < words.length; i++) {
                if (i > 0) {
                    queryString += " AND";
                }
                queryString += " LOWER(description) LIKE LOWER(?)";
            }
        }
        if (ini!=null && fin!=null) {
        	queryString+= "AND id_show IN (SELECT id_show FROM Shows WHERE "
        			+ "date_time_show > (?) AND date_time_show < (?))";
        }
 
        queryString += " ORDER BY name";

        try (PreparedStatement preparedStatement = c.prepareStatement(queryString)) {

            if (words != null) {
            	int i;
                /* Fill "preparedStatement". */
                for (i = 0; i < words.length; i++) {
                    preparedStatement.setString(i + 1, "%" + words[i] + "%");
                }
            if (ini!=null && fin!=null) {
        		preparedStatement.setTimestamp(i+1, Timestamp.valueOf(ini));
        		preparedStatement.setTimestamp(i+2, Timestamp.valueOf(fin));
            }
        }

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read shows. */
            List<Show> found = new ArrayList<Show>();

            while (resultSet.next()) {

                int i = 1;
                Long id_show = new Long(resultSet.getLong(i++));
                String name = resultSet.getString(i++);
                String description = resultSet.getString(i++);
                int duration = resultSet.getInt(i++);
                LocalDateTime date_time_lim = resultSet.getTimestamp(i++).toLocalDateTime();
                int max_tickets = resultSet.getInt(i++);
                float real_price = resultSet.getFloat(i++);
                float discounted_price = resultSet.getFloat(i++);
                double commission_sale = resultSet.getDouble(i++);
                int remaining_tickets = resultSet.getInt(i++);
                LocalDateTime date_time_show = resultSet.getTimestamp(i++).toLocalDateTime();
                

                found.add(new Show(id_show, name,description,duration,date_time_lim,max_tickets,real_price,
                		discounted_price,commission_sale,remaining_tickets,date_time_show));

            }

            /* Return shows. */
            return found;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
	
	@Override
    public void update(Connection connection, Show show)throws InstanceNotFoundException {
        
        /* Create "queryString". */
        String queryString = "UPDATE Shows"
                + " SET name = ?,description = ?,duration = ?,date_time_lim = ?,max_tickets = ?,"
                + "real_price = ?,discounted_price = ?,commission_sale = ?,remaining_tickets = ?, date_time_show = ? WHERE id_show = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

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
            preparedStatement.setLong(i++, show.getId_show());
            

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(show.getId_show(),
                        Show.class.getName());
            }
          
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
	
	@Override
	public void remove(Connection c, Long id_show) throws InstanceNotFoundException {

        /* Create "queryString". */
	
        String queryString = "DELETE FROM Shows WHERE" + " id_show = ?";

        try (PreparedStatement preparedStatement = c.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
	
            int i = 1;
            preparedStatement.setLong(i++,id_show);

            /* Execute query. */
	
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(id_show,
                        Show.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
		
}
	
	


