package es.udc.ws.app.test.model.showservice;

import static es.udc.ws.app.model.util.ModelConstants.SHOW_DATA_SOURCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.app.model.booking.Booking;
import es.udc.ws.app.model.booking.SqlBookingDao;
import es.udc.ws.app.model.booking.SqlBookingDaoFactory;
import es.udc.ws.app.model.show.Show;
import es.udc.ws.app.model.show.SqlShowDao;
import es.udc.ws.app.model.show.SqlShowDaoFactory;
import es.udc.ws.app.model.showservice.ShowService;
import es.udc.ws.app.model.showservice.ShowServiceFactory;
import es.udc.ws.app.model.showservice.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

public class ShowServiceTest {
	
	private static final float DISC_PRICE = 0;
	private static final float DISC_PRICE_AUM = 4;

	private final String VALID_CREDIT_CARD_NUMBER = "1234567890123456";
	private final String NO_EXIST_CREDIT_CARD_NUMBER = "9999999999999999";
	private final String INVALID_CREDIT_CARD_NUMBER = "9999";
	private final String VALID_EMAIL = "prueba@mail.com";
	private final int VALID_TICKETS_NUMBER=2;
	private final int INSUFFICIENT_TICKETS_NUMBER=4;
	private static SqlBookingDao bookingDao = null;
	private static SqlShowDao showDao = null;
	private static ShowService showService = null;
	
	@BeforeClass
	public static void init() {

		/*
		 * Create a simple data source and add it to "DataSourceLocator" (this
		 * is needed to test "es.udc.ws.movies.model.movieservice.MovieService"
		 */
		DataSource dataSource = new SimpleDataSource();

		/* Add "dataSource" to "DataSourceLocator". */
		DataSourceLocator.addDataSource(SHOW_DATA_SOURCE, dataSource);

		showService = ShowServiceFactory.getService();
		showDao = SqlShowDaoFactory.getDao();
		bookingDao = SqlBookingDaoFactory.getDao();

	}
	
	/*private Show getValidShow(String name, String descrp) {
		return new Show(name,descrp,85 ,LocalDateTime.of(2019,02,01,20,00),10,5,1,1,8);
	}*/
	private Show getValidShow(String name, String descrp) {
        return new Show((long)0,name,descrp,85 ,LocalDateTime.of(2019,12,1,0,0),10,5,1,1,10,LocalDateTime.of(2020,12,30,0,0));
    }	
	private Show getValidShow() {
		return getValidShow("Show name","Show descrp");
	}

	
	@Test
	public void testCreateShowAndFindShow() throws InputValidationException, InstanceNotFoundException {

		Show show = getValidShow();
		Show createdshow = null;
		DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
		try {
			createdshow = showService.createShow(show);
			Show foundshow = showService.findShow(createdshow.getId_show());
			assertEquals(createdshow.getId_show(), foundshow.getId_show());
		}finally {
			// Clear Database
			try (Connection c = dataSource.getConnection()){
				if (createdshow!=null) {
					showDao.remove(c, createdshow.getId_show());
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
	

	@Test (expected = InstanceNotFoundException.class)
	public void testFindShow() throws InstanceNotFoundException,InputValidationException{
		Show show = getValidShow();
		Show createdshow = null;
		DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
		try {
			long id_show = 23;
			createdshow = showService.createShow(show);
			Show found_show = showService.findShow(id_show);
			assertEquals(createdshow.getId_show(),found_show.getId_show());
		}finally {
			// Clear Database
			try (Connection c = dataSource.getConnection()){
				if (createdshow!=null) {
					showDao.remove(c, createdshow.getId_show());
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}	
		}
	
	}
	
	
	@Test
	public void testBookingAndFindBookingsShow() throws InstanceNotFoundException,InputValidationException,ShowExpirationException,SoldOutTicketsException {
		
		Show show1 = showService.createShow(getValidShow());
		Show show2 = showService.createShow(getValidShow());
		long booking1 = 0;
		long booking2 = 0;
		DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
		try (Connection c = dataSource.getConnection()){
			try {
				LocalDateTime beforeBooking = LocalDateTime.of(2018,11,30,20,00);
				
				booking1 = showService.booking(show1.getId_show(),VALID_EMAIL,VALID_CREDIT_CARD_NUMBER,VALID_TICKETS_NUMBER);
				booking2 = showService.booking(show2.getId_show(),VALID_EMAIL,VALID_CREDIT_CARD_NUMBER,VALID_TICKETS_NUMBER);
				
				LocalDateTime afterBooking = LocalDateTime.of(2020,1,1,20,00);
				
				List<Booking> found_bookings = showService.findBookings(VALID_EMAIL);
		 	
				
				assertEquals(booking1,bookingDao.find(c, booking1).getId_booking());
				assertEquals(booking2,bookingDao.find(c, booking2).getId_booking());
				
				assertEquals((long)show1.getId_show(),(long)bookingDao.find(c, booking1).getId_show());
				assertEquals((long)show2.getId_show(),(long)bookingDao.find(c, booking2).getId_show());
				
				assertEquals(VALID_CREDIT_CARD_NUMBER, found_bookings.get(0).getCredit_card());
				
				assertEquals(VALID_EMAIL,found_bookings.get(0).getEmail());
				
				assertEquals(showService.findShow(show1.getId_show()).getRemaining_tickets(),show1.getMax_tickets()-VALID_TICKETS_NUMBER);
				assertEquals(showService.findShow(show2.getId_show()).getRemaining_tickets(),show2.getMax_tickets()-VALID_TICKETS_NUMBER);
				
				assertTrue((found_bookings.get(0).getDate_time_book().compareTo(beforeBooking) >= 0)
						&& (found_bookings.get(0).getDate_time_book().compareTo(afterBooking) <= 0));
				//assertTrue(LocalDateTime.now().isAfter(found_bookings.get(0).getDate_time_book()));
			}finally {
				// Clear Database
					bookingDao.remove(c, booking1);
					bookingDao.remove(c, booking2);
					showDao.remove(c,show1.getId_show());
					showDao.remove(c,show2.getId_show());
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
			
	}    
	
	
	@Test(expected = SoldOutTicketsException.class)
	public void testBookingSoldOut() throws InstanceNotFoundException,InputValidationException,ShowExpirationException,SoldOutTicketsException {
		Show show = getValidShow();
		Show createdshow = null;
		@SuppressWarnings("unused")
		Long booking1 = null;
		DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
		try (Connection c = dataSource.getConnection()){
			try {
				createdshow = showService.createShow(show);
				booking1 = showService.booking(createdshow.getId_show(),VALID_EMAIL,VALID_CREDIT_CARD_NUMBER,11);				
			}finally {
				// Clear Database
				showDao.remove(c,createdshow.getId_show());
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
			
	}    

	
	@Test (expected = DiscountedPriceException.class)
    public void testUpdateShow() throws InputValidationException, DiscountedPriceException,InstanceNotFoundException, MaxTicketsException, ShowExpirationException, SoldOutTicketsException {
        
        
        Show createdshow = null;
        Long booking=null;
        DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
        Show show = showService.createShow(getValidShow());
        
        try {
            Show showToUpdate = new Show(show.getId_show(),show.getName(), show.getDescription(), show.getDuration(),
                    show.getDate_time_lim(), show.getMax_tickets(), show.getReal_price(), show.getDiscounted_price(), show.getCommission_sale(),
                    show.getRemaining_tickets(),show.getDate_time_show());
            
            booking=showService.booking(show.getId_show(),VALID_EMAIL,VALID_CREDIT_CARD_NUMBER,VALID_TICKETS_NUMBER); //HUBO VENTA
            showToUpdate.setDiscounted_price(DISC_PRICE);          
            
            showService.updateShow(showToUpdate);
            Show updatedShow = showService.findShow(show.getId_show());
            assertEquals(showToUpdate.getId_show(), updatedShow.getId_show());

        } finally {
            // Clear Database
            try (Connection c = dataSource.getConnection()){
                if (createdshow!=null) {
                    showDao.remove(c, createdshow.getId_show());
                    bookingDao.remove(c,booking);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    
    
    @Test
    public void testUpdateShow2() throws InputValidationException, DiscountedPriceException, InstanceNotFoundException, MaxTicketsException {
        
        
        DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
        Show show = getValidShow();
        Show createdshow=showService.createShow(show);
        
        try {
            Show showToUpdate = new Show(createdshow.getId_show(),createdshow.getName(), createdshow.getDescription(), createdshow.getDuration(),
                    createdshow.getDate_time_lim(), createdshow.getMax_tickets(), createdshow.getReal_price(), createdshow.getDiscounted_price(), createdshow.getCommission_sale(),
                    createdshow.getRemaining_tickets(),createdshow.getDate_time_show());
            
            Show updatedShow = showService.findShow(createdshow.getId_show());
            showToUpdate.setDate_time_show(createdshow.getDate_time_show());
            showToUpdate.setDiscounted_price(DISC_PRICE_AUM);
            
            showService.updateShow(showToUpdate);

            
            
            assertFalse(showToUpdate.getDiscounted_price() == updatedShow.getDiscounted_price());

        } finally {
            // Clear Database
            try (Connection c = dataSource.getConnection()){
                if (createdshow!=null) {
                    showDao.remove(c, createdshow.getId_show());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
 
    
    @Test (expected=MaxTicketsException.class)
    public void testUpdateShowMaxTickets() throws InputValidationException, DiscountedPriceException, InstanceNotFoundException, MaxTicketsException, ShowExpirationException, SoldOutTicketsException {
        
    	
        DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
        Show show = getValidShow();
        Long booking= null;
        Show createdshow=showService.createShow(show);
        booking = showService.booking(createdshow.getId_show(),VALID_EMAIL,VALID_CREDIT_CARD_NUMBER,5);
        
        try {
        	
            //Show updatedShow = showService.findShow(createdshow.getId_show());
            createdshow.setDate_time_show(createdshow.getDate_time_show());
            createdshow.setDiscounted_price(DISC_PRICE_AUM);
            createdshow.setMax_tickets(INSUFFICIENT_TICKETS_NUMBER);
            
            showService.updateShow(createdshow);


        } finally {
            // Clear Database
            try (Connection c = dataSource.getConnection()){
            	if (booking!=null) {
	                bookingDao.remove(c, booking);
	            }
                if (createdshow!=null) {
                    showDao.remove(c, createdshow.getId_show());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
   
	
	@Test
    public void testFindKey() throws InputValidationException,InstanceNotFoundException {
        Show show = getValidShow("Show1","Target");
        Show show2 = getValidShow("Show2","Other");
        Show show3 = getValidShow("Show3","Wrong Target");
        Show createdshow = null;
        Show createdshow2 = null;
        Show createdshow3 = null;
        DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
        String key = "Target";
        List<Show> foundShowDate = new ArrayList<Show>();
        List<Show> testfoundDate = new ArrayList<Show>();
        try {
            //Establecemos las fechas para los tests
            show.setDate_time_lim(LocalDateTime.of(2019,10,1,0,0));
            show.setDate_time_show(LocalDateTime.of(2019,11,15,0,0));
            
            show2.setDate_time_lim(LocalDateTime.of(2019,10,1,0,0));
            show2.setDate_time_show(LocalDateTime.of(2019,11,15,0,0));
            
            show3.setDate_time_lim(LocalDateTime.of(2020, 10, 1, 0, 0));
            show3.setDate_time_show(LocalDateTime.of(2020, 11, 1, 0, 0));
            
            //Insertamos los shows
            createdshow = showService.createShow(show);
            createdshow2 = showService.createShow(show2);
            createdshow3 = showService.createShow(show3);
            
            //Establecemos el resultado a esperar
            testfoundDate.add(createdshow);
            
            //Ejecutamos el metodo findShows
            foundShowDate=showService.findKey(key, LocalDateTime.of(2019, 11, 1, 0, 0), LocalDateTime.of(2019, 11, 30, 0, 0));
            assertTrue(testfoundDate.equals(foundShowDate));
        
        }finally {
            // Clear Database
            try (Connection c = dataSource.getConnection()){
                if (foundShowDate!=null) {
                    showDao.remove(c, createdshow.getId_show());
                    showDao.remove(c, createdshow2.getId_show());
                    showDao.remove(c, createdshow3.getId_show());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        
    
    }
    
    @Test
    public void testFindKey2() throws InputValidationException,InstanceNotFoundException {
        Show show = getValidShow("Show1","Target");
        Show show2 = getValidShow("Show2","Wrong Other");
        Show show3 = getValidShow("Show3","Wrong Target");
        Show createdshow = null;
        Show createdshow2 = null;
        Show createdshow3 = null;
        DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
        String key = "Wrong Other";
        List<Show> foundShow = new ArrayList<Show>();
        List<Show> testfound = new ArrayList<Show>();
        try {
            //Establecemos las fechas para los tests
            show.setDate_time_lim(LocalDateTime.of(2019,10,1,0,0));
            show.setDate_time_show(LocalDateTime.of(2019,11,15,0,0));
            
            show2.setDate_time_lim(LocalDateTime.of(2019,10,1,0,0));
            show2.setDate_time_show(LocalDateTime.of(2019,11,15,0,0));
            
            show3.setDate_time_lim(LocalDateTime.of(2020, 10, 1, 0, 0));
            show3.setDate_time_show(LocalDateTime.of(2020, 11, 1, 0, 0));
            
            //Insertamos los shows
            createdshow = showService.createShow(show);
            createdshow2 = showService.createShow(show2);
            createdshow3 = showService.createShow(show3);
            
            //Establecemos el resultado a esperar
            testfound.add(createdshow2);
            
            //Ejecutamos el metodo findShows
            foundShow = showService.findKey(key,null,null);
            assertTrue(testfound.equals(foundShow));        
        }finally {
            // Clear Database
            try (Connection c = dataSource.getConnection()){
                if (foundShow!=null) {
                    showDao.remove(c, createdshow.getId_show());
                    showDao.remove(c, createdshow2.getId_show());
                    showDao.remove(c, createdshow3.getId_show());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        
    
    }
    
    @Test
    public void testFindKey3() throws InputValidationException,InstanceNotFoundException {
        Show show = getValidShow("Show1","Target");
        Show show2 = getValidShow("Show2","Other");
        Show show3 = getValidShow("Show3","Wrong Target");
        Show createdshow = null;
        Show createdshow2 = null;
        Show createdshow3 = null;
        DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
        String key = "None";
        List<Show> foundShow = new ArrayList<Show>();
        List<Show> testfound = new ArrayList<Show>();
        try {
            //Establecemos las fechas para los tests
            show.setDate_time_lim(LocalDateTime.of(2019,10,1,0,0));
            show.setDate_time_show(LocalDateTime.of(2019,11,15,0,0));
            
            show2.setDate_time_lim(LocalDateTime.of(2019,10,1,0,0));
            show2.setDate_time_show(LocalDateTime.of(2019,11,15,0,0));
            
            show3.setDate_time_lim(LocalDateTime.of(2020, 10, 1, 0, 0));
            show3.setDate_time_show(LocalDateTime.of(2020, 11, 1, 0, 0));
            
            //Insertamos los shows
            createdshow = showService.createShow(show);
            createdshow2 = showService.createShow(show2);
            createdshow3 = showService.createShow(show3);
            
            //Establecemos el resultado a esperar

            
            //Ejecutamos el metodo findShows
            foundShow = showService.findKey(key,null,null);
            assertTrue(testfound.equals(foundShow));        
        }finally {
            // Clear Database
            try (Connection c = dataSource.getConnection()){
                    showDao.remove(c, createdshow.getId_show());
                    showDao.remove(c, createdshow2.getId_show());
                    showDao.remove(c, createdshow3.getId_show());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @SuppressWarnings("unused")
	@Test (expected = InputValidationException.class)
    public void testFindKeyInputValidation() throws InputValidationException,InstanceNotFoundException {
        Show show = getValidShow("Show1","Target");
        Show show2 = getValidShow("Show2","Other");
        Show show3 = getValidShow("Show3","Wrong Target");
        Show createdshow = null;
        Show createdshow2 = null;
        Show createdshow3 = null;
        DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
        String key = "";
        List<Show> foundShow = new ArrayList<Show>();
        List<Show> testfound = new ArrayList<Show>();
        try {
            //Establecemos las fechas para los tests
            show.setDate_time_lim(LocalDateTime.of(2019,10,1,0,0));
            show.setDate_time_show(LocalDateTime.of(2019,11,15,0,0));
            
            show2.setDate_time_lim(LocalDateTime.of(2019,10,1,0,0));
            show2.setDate_time_show(LocalDateTime.of(2019,11,15,0,0));
            
            show3.setDate_time_lim(LocalDateTime.of(2020, 10, 1, 0, 0));
            show3.setDate_time_show(LocalDateTime.of(2020, 11, 1, 0, 0));
            
            //Insertamos los shows
            createdshow = showService.createShow(show);
            createdshow2 = showService.createShow(show2);
            createdshow3 = showService.createShow(show3);
            
            //Establecemos el resultado a esperar

            
            //Ejecutamos el metodo findShows
            foundShow = showService.findKey(key,null,null);
            
        }finally {
            // Clear Database
            try (Connection c = dataSource.getConnection()){
                    showDao.remove(c, createdshow.getId_show());
                    showDao.remove(c, createdshow2.getId_show());
                    showDao.remove(c, createdshow3.getId_show());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Test
    public void testRedeemTickets() throws InputValidationException, InstanceNotFoundException,BookingUsedException, ShowExpirationException, SoldOutTicketsException, CreditCardMatchException {
    	Show show = showService.createShow(getValidShow());
    	Long booking1 = null;
    	
    	DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
    	try (Connection c = dataSource.getConnection()){
	    	try {
		    	booking1 = showService.booking(show.getId_show(),VALID_EMAIL,VALID_CREDIT_CARD_NUMBER,1);
		    	showService.redeeemTicket(booking1,VALID_CREDIT_CARD_NUMBER);
		    	List<Booking> reserva = showService.findBookings(VALID_EMAIL);
		    	System.out.print(reserva.get(0).isUsed());
		    	assertTrue(bookingDao.find(c, booking1).isUsed());
	    	
	    	}finally {
	            if (booking1!=null) {
	                bookingDao.remove(c, booking1);
	            }
                if (show!=null) {
                	showDao.remove(c,show.getId_show());
                }
	    	}
    	}catch (SQLException e) {
    		throw new RuntimeException(e);
    	}
    	
    }
    
    
    
    @Test (expected = CreditCardMatchException.class)
    public void testRedeemTicketsCreditCard() throws InputValidationException, InstanceNotFoundException,BookingUsedException, ShowExpirationException, SoldOutTicketsException, CreditCardMatchException {
    	Show show = showService.createShow(getValidShow());
    	Long booking1 = null;
    	
    	DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
    	try (Connection c = dataSource.getConnection()){
	    	try {
		    	booking1 = showService.booking(show.getId_show(),VALID_EMAIL,VALID_CREDIT_CARD_NUMBER,1);
		    	showService.redeeemTicket(booking1,NO_EXIST_CREDIT_CARD_NUMBER);
		    	List<Booking> reserva = showService.findBookings(VALID_EMAIL);
		    	System.out.print(reserva.get(0).isUsed());
		    	assertTrue(bookingDao.find(c, booking1).isUsed());
	    	
	    	}finally {
	            if (booking1!=null) {
	                bookingDao.remove(c, booking1);
	            }
                if (show!=null) {
                	showDao.remove(c,show.getId_show());
                }
            }
    	}catch (SQLException e) {
    		throw new RuntimeException(e);
    	}
    	
    }
    
    @Test (expected = BookingUsedException.class)
    public void testRedeemTicketsBookingUsed() throws InputValidationException, InstanceNotFoundException,BookingUsedException, ShowExpirationException, SoldOutTicketsException, CreditCardMatchException {
    	Show show = showService.createShow(getValidShow());
    	Long booking1 = null;
    	
    	DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
    	try (Connection c = dataSource.getConnection()){
	    	try {
		    	booking1 = showService.booking(show.getId_show(),VALID_EMAIL,VALID_CREDIT_CARD_NUMBER,1);
		    	showService.redeeemTicket(booking1,VALID_CREDIT_CARD_NUMBER);
		    	showService.redeeemTicket(booking1,VALID_CREDIT_CARD_NUMBER);
		    	List<Booking> reserva = showService.findBookings(VALID_EMAIL);
		    	System.out.print(reserva.get(0).isUsed());
		    	assertTrue(bookingDao.find(c, booking1).isUsed());
	    	
	    	}finally {
	            if (booking1!=null) {
	                bookingDao.remove(c, booking1);
	            }
                if (show!=null) {
                	showDao.remove(c,show.getId_show());
                }
	    	}
    	}catch (SQLException e) {
    		throw new RuntimeException(e);
    	}
    	
    }
    
    
    @Test (expected = InputValidationException.class)
    public void testRedeemTicketsInputValidation() throws InputValidationException, InstanceNotFoundException,BookingUsedException, ShowExpirationException, SoldOutTicketsException, CreditCardMatchException {
    	Show show = showService.createShow(getValidShow());
    	Long booking1 = null;
    	
    	DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
    	try (Connection c = dataSource.getConnection()){
	    	try {
		    	booking1 = showService.booking(show.getId_show(),VALID_EMAIL,VALID_CREDIT_CARD_NUMBER,1);
		    	showService.redeeemTicket(booking1,INVALID_CREDIT_CARD_NUMBER);
		    	List<Booking> reserva = showService.findBookings(VALID_EMAIL);
		    	System.out.print(reserva.get(0).isUsed());
		    	assertTrue(bookingDao.find(c, booking1).isUsed());
	    	
	    	}finally {
	            if (booking1!=null) {
	                bookingDao.remove(c, booking1);
	            }
                if (show!=null) {
                	showDao.remove(c,show.getId_show());
                }
            }
    	}catch (SQLException e) {
    		throw new RuntimeException(e);
    	}
    	
    }
    
    @Test(expected = ShowExpirationException.class)
	public void testBookingShowExpiration() throws InstanceNotFoundException,InputValidationException,ShowExpirationException,SoldOutTicketsException {
		Show show = getValidShow();
		show.setDate_time_lim(LocalDateTime.of(2018, 12, 12, 0, 0));
		Show createdshow = null;
		Long booking1 = null;
		DataSource dataSource = DataSourceLocator.getDataSource(SHOW_DATA_SOURCE);
		try (Connection c = dataSource.getConnection()){
			try {
				createdshow = showDao.create(c, show);
				booking1 = showService.booking(createdshow.getId_show(),VALID_EMAIL,VALID_CREDIT_CARD_NUMBER,11);				
			}finally {
				// Clear Database
				if (booking1!=null) {
	                bookingDao.remove(c, booking1);
	            }
				showDao.remove(c,createdshow.getId_show());
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
			
	}  
    


    
}
