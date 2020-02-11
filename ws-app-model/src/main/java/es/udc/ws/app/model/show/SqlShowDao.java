package es.udc.ws.app.model.show;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import es.udc.ws.util.exceptions.InstanceNotFoundException;


public interface SqlShowDao {
	public Show create (Connection c, Show e);
	public Show find (Connection c, Long id_show) throws InstanceNotFoundException;;
	public List<Show> findShows (Connection c,String clave, LocalDateTime fecha_inicio, LocalDateTime fecha_fin);
	public void update (Connection c, Show e) throws InstanceNotFoundException;
	public void remove (Connection c, Long id_show) throws InstanceNotFoundException;;
}
