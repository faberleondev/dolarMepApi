package online.gamelogy.dolarMepApi.repository;

import online.gamelogy.dolarMepApi.entity.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
    List<Cotizacion> findByEntidad(String entidad);
}
