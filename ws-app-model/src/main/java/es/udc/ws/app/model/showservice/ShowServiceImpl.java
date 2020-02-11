package es.udc.ws.app.model.showservice;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;
import es.udc.ws.app.model.booking.Booking;
import es.udc.ws.app.model.booking.SqlBookingDao;
import es.udc.ws.app.model.booking.SqlBookingDaoFactory;
import es.udc.ws.app.model.show.Show;
import es.udc.ws.app.model.show.SqlShowDao;
import es.udc.ws.app.model.show.SqlShowDaoFactory;
import es.udc.ws.app.model.showservice.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.validation.PropertyValidator;
import static es.udc.ws.app.model.util.ModelConstants.MAX_REAL_PRICE;
import static es.udc.ws.app.model.util.ModelConstants.MAX_DURATION;
import static es.udc.ws.app.model.util.ModelConstants.SHOW_DATA_SOURCE;

public class ShowServiceImpl implements ShowService {
	
	private final DataSource dataSource;
	private SqlShowDao showDao = null;
	private SqlBookingDao bookingDao = null;
	
	
	public ShowServiceImpl() {
		dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
		showDao = SqlShowDaoFactory.getDao();
		bookingDao = SqlBookingDaoFactory.getDao();
	}


	private void validateShow(Show show) throws InputValidationException {

		PropertyValidator.validateMandatoryString("name", show.getName());
		PropertyValidator.validateMandatoryString("description", show.getDescription());
		PropertyValidator.validateLong("duration",show.getDuration() , 0, MAX_DURATION);
		PropertyValidator.validateDouble("price", show.getReal_price(), 0, MAX_REAL_PRICE);
	}
	
	private void validateEmail(String email) throws InputValidationException {
		if(email.contains("@")==false)
			throw new InputValidationException("Incorret email");
	}

	
	@Override
	public Show createShow(Show show) throws InputValidationException{

        //Validation of show's fields
        if (show.getName().trim().isEmpty())throw new InputValidationException("Nombre inválido");
        if (show.getDescription().trim().isEmpty())throw new InputValidationException("Descripción inválida");
        if (show.getDuration()<=0)throw new InputValidationException("Duración inválida");
        if (show.getMax_tickets()<=0)throw new InputValidationException("Tickets máximos inválidos");
        if (show.getRemaining_tickets()<=0)throw new InputValidationException("Tickets restantes inválidos");
        if (show.getDate_time_lim().isBefore(LocalDateTime.now()))throw new InputValidationException("Fecha límite inválida");
        if (show.getDate_time_show().isBefore(show.getDate_time_lim()))throw new InputValidationException("Fecha inválida");
        if (show.getDiscounted_price()>show.getReal_price() || show.getDiscounted_price()<0)throw new InputValidationException("El precio de descuento no puede ser mayor que el real o negativo");
        if (show.getReal_price()<0)throw new InputValidationException("El precio no puede ser negativo");


		try (Connection connection = dataSource.getConnection()) {

			try {

				/* Prepare connection. */
				connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				/* Do work. */
				Show createdShow = showDao.create(connection, show);

				/* Commit. */
				connection.commit();
				connection.close();

				return createdShow;

			} catch (SQLException e) {
				connection.rollback();
                connection.close();
				throw new RuntimeException(e);
			} catch (RuntimeException | Error e) {
				connection.rollback();
                connection.close();
				throw e;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Override
	public Show findShow(Long id_show) throws InstanceNotFoundException {

		try (Connection connection = dataSource.getConnection()) {
			return showDao.find(connection, id_show);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<Show> findKey(String key, LocalDateTime ini, LocalDateTime fin) throws InputValidationException {
		try (Connection connection = dataSource.getConnection()) {
			if((key=="") || (key==null))
				throw new InputValidationException("Invalid value");
			return showDao.findShows(connection, key,ini,fin);
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
    public void updateShow(Show show) throws InputValidationException, InstanceNotFoundException, DiscountedPriceException, MaxTicketsException {

        validateShow(show);
        
      //Validation of show's fields
        if (show.getName().trim().isEmpty())throw new InputValidationException("Nombre inválido");
        if (show.getDescription().trim().isEmpty())throw new InputValidationException("Descripción inválida");
        if (show.getDuration()<=0)throw new InputValidationException("Duración inválida");
        if (show.getMax_tickets()<=0)throw new InputValidationException("Tickets máximos inválidos");
        if (show.getRemaining_tickets()<=0)throw new InputValidationException("Tickets restantes inválidos");
        if (show.getDate_time_lim().isBefore(LocalDateTime.now()))throw new InputValidationException("Fecha límite inválida");
        if (show.getDate_time_show().isBefore(show.getDate_time_lim()))throw new InputValidationException("Fecha inválida");
        if (show.getDiscounted_price()>show.getReal_price() || show.getDiscounted_price()<0)throw new InputValidationException("El precio de descuento no puede ser mayor que el real o negativo");
        if (show.getReal_price()<0)throw new InputValidationException("El precio no puede ser negativo");
        
        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Show old = showDao.find(connection,show.getId_show());
                if (bookingDao.getShowBookings(connection, show.getId_show())>0) {
                    if (old.getDiscounted_price() > show.getDiscounted_price()){
                        throw new DiscountedPriceException(show.getId_show());
                    }
                }
                if (show.getMax_tickets()<bookingDao.getShowBookings(connection, show.getId_show())){
                    throw new MaxTicketsException(show.getId_show());
                }
                show.setRemaining_tickets(show.getMax_tickets()-bookingDao.getShowBookings(connection, show.getId_show()));
                
                showDao.update(connection, show);

                /* Commit. */
                connection.commit();
                connection.close();

            } catch (DiscountedPriceException e) {
            	connection.rollback();
                connection.close();
                throw e;
            } catch (SQLException e) {
            	connection.rollback();
                connection.close();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
            	connection.rollback();
                connection.close();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

	@Override
	public long booking(long id_show, String email, String credit_card, int ticket)throws InstanceNotFoundException,InputValidationException,ShowExpirationException,SoldOutTicketsException{
		Booking created_booking=null;
		PropertyValidator.validateCreditCard(credit_card);
		validateEmail(email);
		
		try(Connection connection = dataSource.getConnection()){
			try {
				/* Prepare connection. */
				connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
				/*Do work*/
				Show show = showDao.find(connection,id_show);
				/*Validar fecha*/
				if(LocalDateTime.now().compareTo(show.getDate_time_lim())<0){
					/*Validar tickets restantes*/
					if(show.getRemaining_tickets()>=ticket){
						created_booking = bookingDao.create(connection,new Booking(email,credit_card,LocalDateTime.now(),id_show,show.getDiscounted_price(),false,ticket));
						show.setRemaining_tickets(show.getRemaining_tickets()-ticket);
						showDao.update(connection,show);
					}else
						throw new SoldOutTicketsException(id_show,ticket);
				}else 
					throw new ShowExpirationException(id_show,show.getDate_time_lim());
				connection.commit();
				connection.close();
				
			} catch (InstanceNotFoundException e) {
				connection.rollback();
				connection.close();
				throw e;
			} catch (SQLException e) {
				connection.rollback();
				connection.close();
				throw new RuntimeException(e);
			} catch (RuntimeException | Error e) {
				connection.rollback();
				connection.close();
				throw e;
			}
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
	    }
		return created_booking.getId_booking();
	}
	
    @Override 
	public List<Booking> findBookings(String email)throws InputValidationException{
    	validateEmail(email);
		try(Connection connection = dataSource.getConnection()){
			return bookingDao.findByUser(connection, email);
	    }catch (SQLException e) {
			throw new RuntimeException(e);
		}	
    }
    
    @Override
    public void	redeeemTicket(long id_booking,String credit_card) throws InputValidationException, InstanceNotFoundException,BookingUsedException, CreditCardMatchException {
    	PropertyValidator.validateCreditCard(credit_card);
    	
    	try(Connection connection = dataSource.getConnection()){
    		try {
    			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
	    		Booking valid_booking =bookingDao.find(connection, id_booking);
	    		if(valid_booking != null) {
	    			if(valid_booking.isUsed()==false) {
	    				if(credit_card.equals(valid_booking.getCredit_card())) {
	    					valid_booking.setUsed(true);
	    					bookingDao.update(connection,valid_booking);
	    				}else {
	    					throw new CreditCardMatchException(id_booking);
	    				}
	    			}else
	    				throw new BookingUsedException(id_booking);
	    		}
	    		connection.commit();
	    		connection.close();
    		}catch(InstanceNotFoundException e) {
    			connection.rollback();
				connection.close();;
    			throw e;
    		}catch(CreditCardMatchException e) {
    			connection.rollback();
				connection.close();
				throw e;
			}
    	}catch(SQLException e) {
    		throw new RuntimeException(e);
    	}
    }
}