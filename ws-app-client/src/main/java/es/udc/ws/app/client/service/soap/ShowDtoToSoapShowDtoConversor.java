package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.client.service.dto.ClientAdminShowDto;


/*
public class ShowDtoToSoapShowDtoConversor {
    public static ServiceShowDto toServiceSoapShowDto(ClientAdminShowDto show) {
        ServiceShowDto soapShowDto = new ServiceShowDto();
        soapMovieDto.setMovieId(movie.getMovieId());
        soapMovieDto.setTitle(movie.getTitle());
        soapMovieDto.setRuntime((short) (movie.getRuntimeHours() * 60 +
                movie.getRuntimeMinutes()));
        soapMovieDto.setDescription(movie.getDescription());
        soapMovieDto.setPrice(movie.getPrice());
        return soapShowDto;
    }

    public static ClientAdminShowDto toClientAdminShowDto(ServiceShowDto show) {
        return new ClientAdminShowDto(movie.getMovieId(), movie.getTitle(),
                (short) (movie.getRuntime() / 60), (short) (movie.getRuntime() % 60), 
                movie.getDescription(), movie.getPrice());
    }

    public static List<ClientAdminShowDto> toClientAdminShowDtos(List<ServiceShowDto> shows) {
        List<ClientAdminShowDto> showDtos = new ArrayList<>(shows.size());
        for (int i = 0; i < shows.size(); i++) {
            ServiceShowDto show = shows.get(i);
            showDtos.add(toClientAdminShowDto(show));
        }
        return showDtos;
    }
}
*/