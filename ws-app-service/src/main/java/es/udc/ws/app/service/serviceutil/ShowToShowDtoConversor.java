package es.udc.ws.app.service.serviceutil;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.service.dto.ServiceShowDto;
import es.udc.ws.app.model.show.Show;

public class ShowToShowDtoConversor {

    public static List<ServiceShowDto> toShowDtos(List<Show> shows) {
        List<ServiceShowDto> showDtos = new ArrayList<>(shows.size());
        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);
            showDtos.add(toShowDto(show));
        }
        return showDtos;
    }

    public static ServiceShowDto toShowDto(Show show) {
        return new ServiceShowDto(show.getId_show(), show.getName(), show.getDescription(),show.getDate_time_show(),show.getDuration(),show.getDate_time_lim(),show.getMax_tickets(),show.getReal_price(), show.getDiscounted_price(),show.getCommission_sale(),show.getRemaining_tickets());
    }

    public static Show toShow(ServiceShowDto show) {
        return new Show(show.getId_show(), show.getName(), show.getDescription(),show.getDuration(),show.getDate_time_lim(),show.getMax_tickets(),show.getReal_price(), show.getDiscounted_price(),show.getCommission_sale(),show.getRemaining_tickets(),show.getDate_time_show());
    }

}

