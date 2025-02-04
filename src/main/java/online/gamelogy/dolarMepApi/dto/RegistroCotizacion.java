package online.gamelogy.dolarMepApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import online.gamelogy.dolarMepApi.config.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public record RegistroCotizacion(
        String entidad,
        BigDecimal precioCompra,
        BigDecimal precioVenta,
        BigDecimal spread,
        BigDecimal porcentajeSpread,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime fechaActualizacion
) {
    public RegistroCotizacion(String entidad, BigDecimal precioCompra,
                              BigDecimal precioVenta, LocalDateTime fechaActualizacion) {
        this(entidad, precioCompra, precioVenta,
                calcularSpread(precioCompra, precioVenta),
                calcularPorcentajeSpread(precioCompra, precioVenta),
                fechaActualizacion);
    }

    private static BigDecimal calcularSpread(BigDecimal compra, BigDecimal venta) {
        return venta.subtract(compra);
    }

    private static BigDecimal calcularPorcentajeSpread(BigDecimal compra, BigDecimal venta) {
        return (compra.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                calcularSpread(compra, venta).divide(compra, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
    }
}