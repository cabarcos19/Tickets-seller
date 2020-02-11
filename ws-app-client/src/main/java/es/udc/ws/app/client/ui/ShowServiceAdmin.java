package es.udc.ws.app.client.ui;

import es.udc.ws.app.client.service.ClientAdminService;
import es.udc.ws.app.client.service.ClientAdminServiceFactory;
import es.udc.ws.app.client.service.dto.ClientAdminShowDto;
import es.udc.ws.app.client.service.dto.ClientBookingDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;


public class ShowServiceAdmin {

    public static void main(String[] args) {

        if(args.length == 0) {
            printUsageAndExit();
        }
        ClientAdminService clientAdminService =ClientAdminServiceFactory.getService();
        if("-c".equalsIgnoreCase(args[0])) {
            validateArgs(args,10, new int[] {4,6,7,8,9});
            try {
            	
            	Long id_show = clientAdminService.createShow(new ClientAdminShowDto(null,args[1],args[2],LocalDateTime.parse(args[3]),Integer.valueOf(args[4]),LocalDateTime.parse(args[5]),Integer.valueOf(args[6]),Float.valueOf(args[7]),Float.valueOf(args[8]),Double.valueOf(args[9]),Integer.valueOf(args[6])));

                System.out.println("Show " + id_show + " created sucessfully");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-u".equalsIgnoreCase(args[0])) {
           validateArgs(args,11, new int[] {1,5,8,9,10});

           try {
                clientAdminService.updateShow(new ClientAdminShowDto(Long.parseLong(args[1]),args[2],args[3],LocalDateTime.parse(args[4]),Integer.valueOf(args[5]),LocalDateTime.parse(args[6]),Integer.valueOf(args[7]),Float.valueOf(args[8]),Float.valueOf(args[9]),Double.valueOf(args[10]),Integer.valueOf(args[7])));

                System.out.println("Show " + args[1] + " updated sucessfully");

            } catch (NumberFormatException | InputValidationException |
                     InstanceNotFoundException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-f".equalsIgnoreCase(args[0])) {
            validateArgs(args,2, new int[] {1});

            try {
                ClientAdminShowDto show = clientAdminService.findShow(Long.parseLong(args[1]));
                System.out.println("Found " + show +
                        " with id '" + args[1] + "'");
                System.out.println("Id: " + show.getId_show() +
                            ", Name: " + show.getName() +
                            ", Description: " + (show.getDescription()) +
                            ", Date_time_show: " + show.getDate_time_show() +
                            ", Date_time_lim: " + show.getDate_time_lim() +
                            ", Max_tickets: " + show.getMax_tickets() +
                            ", Real_price: " + show.getReal_price() +
                            ", Discounted_price: " + show.getDiscounted_price() +
                            ", Commission_sale: " + show.getCommission_sale() +
                            ", Remaining_tickets: " + show.getRemaining_tickets());
                
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }else if("-r".equalsIgnoreCase(args[0])) {
            validateArgs(args, 3, new int[] {1});

            try {
                clientAdminService.redeeemTicket(Long.parseLong(args[1]),
                        args[2]);

                System.out.println("Ticket(s) " + args[1] +
                        " redeeem sucessfully ");

            } catch (NumberFormatException | InstanceNotFoundException |
                     InputValidationException ex) {
                ex.printStackTrace(System.err);
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
                "    [create] ShowServiceAdmin -c <name> <description> <date_time_show> <duration> <date_time_lim> <max_tickets> <real_price> <discounted_price> <commission_sale>\n" +
                "    [update] ShowServiceAdmin -u <id_show> <name> <description> <date_time_show> <duration> <date_time_lim> <max_tickets> <real_price> <discounted_price> <commission_sale>\n" +
                "    [find]   ShowServiceAdmin -f <id_show>\n" +
                "    [redeeem]ShowServiceAdmin -r <id_booking> <credit_card_number>\n");
    }

}
