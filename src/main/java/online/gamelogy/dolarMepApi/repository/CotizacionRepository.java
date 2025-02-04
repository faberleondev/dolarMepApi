package online.gamelogy.dolarMepApi.repository;

import online.gamelogy.dolarMepApi.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

//POO Utilizado: Herencia
//SOLID Utilizado: Open (implementacion) Close (modificacion)
@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
    List<Cotizacion> findByEntidad(String entidad);
    List<Cotizacion> findByBancoIdOrderByFechaActualizacionDesc(Long bancoId);
}
