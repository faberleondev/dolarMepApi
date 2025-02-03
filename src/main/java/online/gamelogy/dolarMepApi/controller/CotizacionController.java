package online.gamelogy.dolarMepApi.controller;

import jakarta.validation.Valid;
import online.gamelogy.dolarMepApi.dto.RegistroCotizacion;
import online.gamelogy.dolarMepApi.model.Cotizacion;
import online.gamelogy.dolarMepApi.dto.client.RegistroCotizacionClient;
import online.gamelogy.dolarMepApi.service.CotizacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
/*
//********************************************************************************************
// AQUI SE ENCUENTRAN TODOS LOS METODOS ENDPOINT QUE EJECUTAREMOS CON CURL, INSOMNIA O POSTMAN
//********************************************************************************************

SOLID Utilizado: Dependency Inversion

 */

@RestController
@RequestMapping("/api/cotizaciones")
public class CotizacionController {
//    @Autowired
    private final CotizacionService cotizacionService;
//    Constructor Genérico
    public CotizacionController(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    /* METODO REGISTRAR COTIZACION
        http POST

         */
    //
    @PostMapping("/registrar-manual")
    public ResponseEntity<Cotizacion> guardarCotizacionManual(@Valid @RequestBody RegistroCotizacionClient registroCotizacionClient){
        Cotizacion cotizacion = cotizacionService.guardarCotizacionManual(registroCotizacionClient);
        return ResponseEntity.ok(cotizacion);
    }

    /*OBTERNER TODAS LAS COTIZACIONES,
     DASHBOARD PRINCIPAL
     se trata de un metodo http GET
     */

    @GetMapping
    public ResponseEntity<List<Cotizacion>> obtenerTodas() {
        return ResponseEntity.ok(cotizacionService.obtenerTodas());
    }

    //Buscar por entidad (NOMBRE DEL BANCO)
    @GetMapping("/buscar/{entidad}")
    public ResponseEntity<List<RegistroCotizacion>> obtenerRegistroCotizacion(@PathVariable String entidad) {
        List<Cotizacion> cotizaciones = cotizacionService.obtenerPorEntidad(entidad);

        List<RegistroCotizacion> registros = cotizaciones.stream()
                .map(c -> new RegistroCotizacion(
                        c.getEntidad(),
                        c.getPrecioCompra(),
                        c.getPrecioVenta(),
                        c.getFechaActualizacion()
                ))
                .toList();

        return ResponseEntity.ok(registros);
    }

    // OK, falta devolver JSON
    @GetMapping("/{id}")
    public ResponseEntity<Cotizacion> obtenerPorId(@PathVariable Long id) {
        Optional<Cotizacion> cotizacion = cotizacionService.obtenerPorId(id);
        return cotizacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // OK, falta devolver JSON
    @GetMapping("/entidad/{entidad}")
    public ResponseEntity<List<Cotizacion>> obtenerPorEntidad(@PathVariable String entidad) {
        return ResponseEntity.ok(cotizacionService.obtenerPorEntidad(entidad));
    }
//     OK,
    @DeleteMapping("/{id}")
    public ResponseEntity<Cotizacion> eliminarCotizacion(@PathVariable Long id) {
        cotizacionService.eliminarCotizacion(id);
        return ResponseEntity.noContent().build();
    }
    //Historial de Cotizaciones!!
    @GetMapping("/historial/{entidad}")
    public ResponseEntity<List<Cotizacion>> obtenerHistorial(@PathVariable String entidad) {
        return ResponseEntity.ok(cotizacionService.obtenerPorEntidad(entidad));
    }
    // OK
    @GetMapping("/buscar/compra")
    public ResponseEntity<List<RegistroCotizacion>> buscarPorPrecioCompra(@RequestParam(name = "precioCompra",required = false, defaultValue = "0.0") BigDecimal precioCompra) {
        return ResponseEntity.ok(cotizacionService.obtenerPorPrecioCompra(precioCompra));
    }
    // ok
    @GetMapping("/buscar/venta")
    public ResponseEntity<List<RegistroCotizacion>> buscarPorPrecioVenta(@RequestParam(name = "precioVenta", required = false, defaultValue = "0.0") BigDecimal precioVenta) {
        return ResponseEntity.ok(cotizacionService.obtenerPorPrecioVenta(precioVenta));
    }
    //METODO ENDPOINT QUE ORDENA DE PRECIO MAS BARATO A MAS CARO
    @GetMapping("/ordenar/compra-venta-asc")
    public ResponseEntity<List<RegistroCotizacion>> obtenerOrdenadosPorCompraVentaAsc() {
        return ResponseEntity.ok(cotizacionService.ordenarPorCompraVentaAsc());
    }
    // FUNCIONA OK
    @GetMapping("/ordenar/spread-desc")
    public ResponseEntity<List<RegistroCotizacion>> obtenerOrdenadosPorSpreadDesc() {
        return ResponseEntity.ok(cotizacionService.ordenarPorSpreadDesc());
    }
}

//    @PutMapping
//    @Transactional
//    public ResponseEntity<Cotizacion> actualizarPrecio(@PathVariable Long id){
//        cotizacionService.actualizarPrecio(precioCompra);
//        return
//    }

//    public void actualizarPrecio(Cotizacion cotizacion) {
//        cotizacion.setFechaActualizacion(LocalDateTime.now());
//        return cotizacionRepository.save(cotizacion);
//    }