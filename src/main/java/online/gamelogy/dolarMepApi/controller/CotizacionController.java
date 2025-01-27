package online.gamelogy.dolarMepApi.controller;

import online.gamelogy.dolarMepApi.dto.RegistroCotizacion;
import online.gamelogy.dolarMepApi.entity.Cotizacion;
import online.gamelogy.dolarMepApi.dto.RegistroCotizacionClient;
import online.gamelogy.dolarMepApi.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
//********************************************************************************************
// AQUI SE ENCUENTRAN TODOS LOS METODOS ENDPOINT QUE EJECUTAREMOS CON CURL, INSOMNIA O POSTMAN
//********************************************************************************************
@RestController
@RequestMapping("/api/cotizaciones")
public class CotizacionController {
    @Autowired
    private CotizacionService cotizacionService;

    //involucra el metodo http POST, el metodo mas importante!
    @PostMapping("/registrar-cotizacion")
    public ResponseEntity<Cotizacion> guardarCotizacion(@RequestBody Cotizacion cotizacion) {
        return ResponseEntity.ok(cotizacionService.guardarCotizacion(cotizacion)).toString();
    }

    @GetMapping("/registro/{entidad}")
    public ResponseEntity<List<RegistroCotizacion>> obtenerRegistroCotizacion(@PathVariable String entidad) {
        List<Cotizacion> cotizaciones = cotizacionService.obtenerPorEntidad(entidad);

        List<RegistroCotizacion> registros = cotizaciones.stream()
                .map(c -> new RegistroCotizacion(
                        c.getEntidad(),
                        c.getPrecioCompra(),
                        c.getPrecioVenta(),
                        c.getFechaActualizacion(LocalDateTime.now())
                ))
                .toList();

        return ResponseEntity.ok(registros);
    }


    //se trata de un metodo http GET
    @GetMapping
    public ResponseEntity<List<Cotizacion>> obtenerTodas() {
        return ResponseEntity.ok(cotizacionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cotizacion> obtenerPorId(@PathVariable Long id) {
        Optional<Cotizacion> cotizacion = cotizacionService.obtenerPorId(id);
        return cotizacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/entidad/{entidad}")
    public ResponseEntity<List<Cotizacion>> obtenerPorEntidad(@PathVariable String entidad) {
        return ResponseEntity.ok(cotizacionService.obtenerPorEntidad(entidad));
    }

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

    @GetMapping("/buscar/compra")
    public ResponseEntity<List<RegistroCotizacion>> buscarPorPrecioCompra(@RequestParam BigDecimal precio) {
        return ResponseEntity.ok(cotizacionService.obtenerPorPrecioCompra(precio));
    }

    @GetMapping("/buscar/venta")
    public ResponseEntity<List<RegistroCotizacion>> buscarPorPrecioVenta(@RequestParam BigDecimal precio) {
        return ResponseEntity.ok(cotizacionService.obtenerPorPrecioVenta(precio));
    }

    @GetMapping("/ordenar/compra-venta-asc")
    public ResponseEntity<List<RegistroCotizacion>> obtenerOrdenadosPorCompraVentaAsc() {
        return ResponseEntity.ok(cotizacionService.ordenarPorCompraVentaAsc());
    }

    @GetMapping("/ordenar/spread-desc")
    public ResponseEntity<List<RegistroCotizacion>> obtenerOrdenadosPorSpreadDesc() {
        return ResponseEntity.ok(cotizacionService.ordenarPorSpreadDesc());
    }
}
    //        DatosRespuestaCotizacion datosRespuestaCotizacion = new DatosRespuestaCotizacion(
//                cotizacion.getId(),
//                cotizacion.getEntidad(),
//                cotizacion.getPrecioCompra(),
//                cotizacion.getPrecioVenta(),
//                cotizacion.getFechaActualizacion(LocalDateTime.now()));
//        System.out.println("Datos cargados correctamente");
//        // RETURN 201 Creado
//
//        // URL donde encontrar al medico
//        //        URI urlLocalHostMedicoCreado = URI.create("http://localhost:8080/medicos/" + medicos.getId());
//        URI urlCotizacionCreada = uriComponentsBuilder.path("/entidad/{id}").buildAndExpand(cotizacion.getId()).toUri();
//        return ResponseEntity.created(urlCotizacionCreada).body(datosRespuestaCotizacion);

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