package es.udc.ws.app.client.ui;

import es.udc.ws.app.client.service.ClientUserService;
import es.udc.ws.app.client.service.ClientUserServiceFactory;
import es.udc.ws.app.client.service.dto.ClientUserFindDto;
import es.udc.ws.app.client.service.dto.ClientBookingDto;

import java.time.LocalDateTime;
import java.util.List;

public class ShowServiceUser {

    public static void main(String[] args) {

        if(args.length == 0) {
            printUsageAndExit();
        }
        ClientUserService ClientUserService =ClientUserServiceFactory.getService();
        if("-b".equalsIgnoreCase(args[0])) {
        validateArgs(args,5, new int[] {1,4});

        try {
        	ClientBookingDto booking = ClientUserService.book(new ClientBookingDto(Long.parseLong("1"),args[2],args[3],LocalDateTime.now(),Long.parseLong(args[1]),Float.valueOf("1"),Boolean.valueOf("False"),Integer.valueOf(args[4])));
            System.out.println("Booking created succesfully");
            System.out.println("Id: " + booking.getId_booking() +
                        ", Email: " + booking.getEmail() +
                        ", CreditCard: " + (booking.getCredit_card()) +
                        ", Date_time_book: " + booking.getDate_time_book() +
                        ", Id_show: " + booking.getId_show() +
                        ", Discounted_price: " + booking.getDiscounted_price() +
                        ", Used: " + booking.isUsed() +
                        ", Num_tickets: " + booking.getNum_tickets());
            
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }


        } else if("-k".equalsIgnoreCase(args[0])) {
	        validateArgs(args,2, new int[] {});
	
	        try {
	            List<ClientUserFindDto> shows = ClientUserService.findKey(args[1]);
	            for (int i=0;i<shows.size();i++) { 
	            	ClientUserFindDto show=shows.get(i);
		            System.out.println("Found " + show +
		                    " searching with keyword '" + args[1] + "'");
		            System.out.println("Show-->: "+
		                        " Name: " + show.getName() +
		                        ", Description: " + (show.getDescription()) +
		                        ", Date_time_show: " + show.getDate_time_show() +
		                        ", Date_time_lim: " + show.getDate_time_lim() +
		                        ", Max_tickets: " + show.getMax_tickets() +
		                        ", Real_price: " + show.getReal_price() +
		                        ", Discounted_price: " + show.getDiscounted_price());
	            }        
	        } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
     }else if("-e".equalsIgnoreCase(args[0])) {
	        validateArgs(args,2, new int[] {});
	    	
	        try {
	            List<ClientBookingDto> bookings = ClientUserService.findEmail(args[1]);
	            for (int i=0;i<bookings.size();i++) { 
	            	ClientBookingDto booking = bookings.get(i);
		            System.out.println("Found " + booking +
		                    " searching with keyword '" + args[1] + "'");
		            System.out.println("Id: " + booking.getId_booking() +
                            ", Email: " + booking.getEmail() +
                            ", CreditCard: " + (booking.getCredit_card()) +
                            ", Date_time_book: " + booking.getDate_time_book() +
                            ", Id_show: " + booking.getId_show() +
                            ", Discounted_price: " + booking.getDiscounted_price() +
                            ", Used: " + booking.isUsed() +
                            ", Num_tickets: " + booking.getNum_tickets());
	            }        
	        } catch (Exception ex) {
             ex.printStackTrace(System.err);
         }
 	}
    }

    public static void validateArgs(String[] args, int expectedArgs,
                                    int[] numericArguments) {
        if(expectedArgs != args.length) {
            printUsageAndExit();
        }
        for(int i = 0 ; i< numericArguments.length ; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch(NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "    [booking] ShowServiceUser -b <id_show> <email> <credit_card> <ticket>\n" +
                "    [find]   ShowServiceUser -f <id_show>\n" +
                "    [findBookings]ShowServiceUser -e <email>\n");
    }

}