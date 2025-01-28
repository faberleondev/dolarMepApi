package online.gamelogy.dolarMepApi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.gamelogy.dolarMepApi.dto.RegistroCotizacionClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Column(nullable = false)
    private BigDecimal spread;

//    @Column(nullable = false)
    private BigDecimal porcentajeSpread;

//    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;

    public Cotizacion() {}

    public Cotizacion(RegistroCotizacionClient registroCotizacionClient) {
        this.entidad = registroCotizacionClient.entidad();
        this.precioCompra = registroCotizacionClient.precioCompra();
        this.precioVenta = registroCotizacionClient.precioVenta();
        this.fechaActualizacion = LocalDateTime.now(); //Establece la fecha actual, en el momento preciso.
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

    //El toString personalizado, con el formato de fecha que aún no encontré para LATAM xD
    @Override
    public String toString() {
        return "<<<Cotizacion del Banco/Exchange:>>>" +
                "\n id interno de la Base de Datos = " + id +
                ", \n Nombre = '" + entidad + '\'' +
                " \n Precio de Compra: = $" + precioCompra +
                " \n Precio de Venta: = $" + precioVenta +
                " \n Fecha de actualizacion: = " + fechaActualizacion.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}