package es.udc.ws.app.service.serviceutil;

import es.udc.ws.app.service.dto.ServiceBookingDto;
import java.util.ArrayList;
import java.util.List;
import es.udc.ws.app.model.booking.Booking;

public class BookingToBookingDtoConversor {

    public static ServiceBookingDto toBookingDto(Booking booking) {
        return new ServiceBookingDto(booking.getId_booking(),booking.getEmail(),
        		booking.getCredit_card(),booking.getDate_time_book(),booking.getId_show(),
        		booking.getDiscounted_price(),booking.isUsed(),booking.getNum_tickets());
    }
    
    public static List<ServiceBookingDto> toBookingDtos(List<Booking> bookings) {
        List<ServiceBookingDto> bookingDtos = new ArrayList<>(bookings.size());
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            bookingDtos.add(toBookingDto(booking));
        }
        return bookingDtos;
    }
    
    public static Booking toBooking(ServiceBookingDto booking) {
        return new Booking(booking.getId_booking(), booking.getEmail(), booking.getCredit_card(),booking.getDate_time_book(),booking.getId_show(),booking.getDiscounted_price(),booking.isUsed(),booking.getNum_tickets());
    }

}
