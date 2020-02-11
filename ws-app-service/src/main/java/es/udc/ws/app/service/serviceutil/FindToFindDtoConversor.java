package es.udc.ws.app.service.serviceutil;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.model.show.Show;
import es.udc.ws.app.service.dto.ServiceFindDto;

public class FindToFindDtoConversor {
	
	public static List<ServiceFindDto> toFindDtos(List<Show> shows) {
		List<ServiceFindDto> findDtos = new ArrayList<>(shows.size());
		for (int i = 0; i < shows.size(); i++) {
			Show show = shows.get(i);
			findDtos.add(toFindDto(show));
		}
		return findDtos;
	}

	public static ServiceFindDto toFindDto(Show show) {
		return new ServiceFindDto(show.getId_show(), show.getName(), show.getDescription(),show.getDuration(),show.getDate_time_lim(),show.getMax_tickets(),show.getReal_price(), show.getDiscounted_price(),show.getDate_time_show());
	}
	
	
	public static Show toShow(ServiceFindDto show) {
		return new Show(show.getId_show(), show.getName(), show.getDescription(),show.getDuration(),show.getDate_time_lim(),show.getMax_tickets(),show.getReal_price(), show.getDiscounted_price(),show.getDate_time_show());
	}

}
