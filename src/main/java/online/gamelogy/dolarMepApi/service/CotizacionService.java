package online.gamelogy.dolarMepApi.service;
import online.gamelogy.dolarMepApi.dto.RegistroCotizacion;
import online.gamelogy.dolarMepApi.dto.RegistroCotizacionClient;
import online.gamelogy.dolarMepApi.model.Cotizacion;
import online.gamelogy.dolarMepApi.repository.CotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// POO Utilzado: Polimorfismo (@Override y overloading, en este caso no se está usando @Overrride)
// SOLID: Interface Segreggation
@Service
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Bean
    public ApplicationRunner actualizarCotizacionesAntiguas(CotizacionRepository cotizacionRepository) {
        return args -> {
            List<Cotizacion> cotizaciones = cotizacionRepository.findAll();
            for (Cotizacion cotizacion : cotizaciones) {
                if (cotizacion.getPorcentajeSpread() == null) { // Solo actualizar si está en NULL
                    cotizacion.setSpread(cotizacion.getPrecioVenta().subtract(cotizacion.getPrecioCompra()));
                    cotizacion.setPorcentajeSpread(
                            cotizacion.getPrecioCompra().compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                                    cotizacion.getSpread().divide(cotizacion.getPrecioCompra(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100))
                    );
                    cotizacionRepository.save(cotizacion);
                }
            }
        };
    }

    public List<Cotizacion> obtenerTodas() {
        return cotizacionRepository.findAll();
    }

    public Optional<Cotizacion> obtenerPorId(Long id) {
        return cotizacionRepository.findById(id);
    }

    public List<Cotizacion> obtenerPorEntidad(String entidad) {
        return cotizacionRepository.findByEntidad(entidad);
    }

    public Cotizacion guardarCotizacion(RegistroCotizacionClient registroCotizacionClient) {
        Cotizacion cotizacion = new Cotizacion(registroCotizacionClient);
        return cotizacionRepository.save(cotizacion);
    }

    // CUIDADO CON ESTE METODO, ELIMINA ROWS DE LA TABLA DE DB//
    public void eliminarCotizacion(Long id) {
        cotizacionRepository.deleteById(id);
    }

    public List<RegistroCotizacion> obtenerPorPrecioCompra(BigDecimal precioCompra) {
        return cotizacionRepository.findAll().stream()
            .filter(c -> c.getPrecioCompra().compareTo(precioCompra) >= 0) // Busca valores mayores o iguales
            .sorted(Comparator.comparing(Cotizacion::getPrecioCompra)) // Ordena de menor a mayor
            .map(c -> new RegistroCotizacion(
                    c.getEntidad(),
                    c.getPrecioCompra(),
                    c.getPrecioVenta(),
                    c.getFechaActualizacion(LocalDateTime.now())))
            .collect(Collectors.toList());
    }

    public List<RegistroCotizacion> obtenerPorPrecioVenta(BigDecimal precio) {
        return cotizacionRepository.findAll().stream()
                .filter(c -> c.getPrecioVenta().compareTo(precio) >= 0)
                .sorted(Comparator.comparing(Cotizacion::getPrecioVenta))
                .map(c -> new RegistroCotizacion(
                        c.getEntidad(),
                        c.getPrecioCompra(),
                        c.getPrecioVenta(),
                        c.getFechaActualizacion(LocalDateTime.now())))
                .collect(Collectors.toList());
    }
    //METODO QUE ORDENA DE PRECIO MAS BARATO A MAS CARO
    public List<RegistroCotizacion> ordenarPorCompraVentaAsc() {
        return cotizacionRepository.findAll().stream()
                .sorted(Comparator.comparing(Cotizacion::getPrecioCompra)
                        .thenComparing(Cotizacion::getPrecioVenta)) // Ordena compra y luego venta
                .map(c -> new RegistroCotizacion(
                        c.getEntidad(),
                        c.getPrecioCompra(),
                        c.getPrecioVenta(),
                        c.getFechaActualizacion(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    public List<RegistroCotizacion> ordenarPorSpreadDesc() {
        return cotizacionRepository.findAll().stream()
                .sorted(Comparator.comparing((Cotizacion c) -> c.getPrecioVenta().subtract(c.getPrecioCompra())).reversed()) // Spread descendente
                .map(c -> new RegistroCotizacion(
                        c.getEntidad(),
                        c.getPrecioCompra(),
                        c.getPrecioVenta(),
                        c.getFechaActualizacion(LocalDateTime.now())))
                .collect(Collectors.toList());
    }
}




