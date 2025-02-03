package online.gamelogy.dolarMepApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import online.gamelogy.dolarMepApi.dto.client.RegistroCotizacionClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

// POO Utilizado: Abstraccion y encapsulamiento
//
@Getter
@AllArgsConstructor
@Entity(name= "Cotizaciones")
@Table(name = "cotizaciones")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entidad;

    @Column(nullable = false)
    private BigDecimal precioCompra;

    @Column(nullable = false)
    private BigDecimal precioVenta;
    @Column(precision = 38, scale = 2, nullable = false)
    private BigDecimal spread;

    @Column(precision = 38, scale = 2, nullable = false)
    private BigDecimal porcentajeSpread;
    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;

    public Cotizacion() {}

    public Cotizacion(RegistroCotizacionClient registroCotizacionClient) {
        this.entidad = registroCotizacionClient.entidad();
        this.precioCompra = registroCotizacionClient.precioCompra();
        this.precioVenta = registroCotizacionClient.precioVenta();
        this.spread = calcularSpread(precioCompra, precioVenta);
        this.porcentajeSpread = calcularPorcentajeSpread(precioCompra, precioVenta);
        this.fechaActualizacion = LocalDateTime.now();
    }

    private static BigDecimal calcularSpread(BigDecimal compra, BigDecimal venta) {
        return venta.subtract(compra);
    }

    private static BigDecimal calcularPorcentajeSpread(BigDecimal compra, BigDecimal venta) {
        return compra.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                calcularSpread(compra, venta).divide(compra, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEntidad() { return entidad; }
    public void setEntidad(String entidad) { this.entidad = entidad; }

    public BigDecimal getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(BigDecimal precioCompra) { this.precioCompra = precioCompra; }

    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }

    public LocalDateTime getFechaActualizacion(LocalDateTime now) { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public BigDecimal getSpread() {
        return spread;
    }

    public void setSpread(BigDecimal spread) {
        this.spread = spread;
    }

    public BigDecimal getPorcentajeSpread() {
        return porcentajeSpread;
    }

    public void setPorcentajeSpread(BigDecimal porcentajeSpread) {
        this.porcentajeSpread = porcentajeSpread;
    }

    //El toString personalizado, con el formato de fecha que aún no encontré para LATAM xD
//    @Override
//    public String toString() {
//        return "<<<Cotizacion del Banco/Exchange:>>>" +
//                "\n id interno de la Base de Datos = " + id +
//                ", \n Nombre = '" + entidad + '\'' +
//                " \n Precio de Compra: = $" + precioCompra +
//                " \n Precio de Venta: = $" + precioVenta +
//                " \n Fecha de actualizacion: = " + fechaActualizacion.format(DateTimeFormatter.ISO_DATE_TIME);
//    }
}