package online.gamelogy.dolarMepApi.dto;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public record RegistroCotizacionClient(@NotBlank(message = "{nombre.obligatorio}")
                                 String entidad,
                                       @NotBlank(message = "{precioCompra.obligatorio}")
                                 BigDecimal precioCompra,
                                       @NotBlank(message = "{precioVenta.obligatorio}")
                                 BigDecimal precioVenta,
                                 BigDecimal spread,
                                 BigDecimal porcentajeSpread,
                                 LocalDateTime fechaActualizacion) {
    public RegistroCotizacionClient(String entidad, BigDecimal precioCompra, BigDecimal precioVenta) {
        this(entidad,
                precioCompra,
                precioVenta,
                precioVenta.subtract(precioCompra), // CALCULO porcentaje SPREAD
                precioCompra.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : precioVenta.subtract(precioCompra).divide(
                        precioCompra, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)),
                LocalDateTime.now() // Se asigna aquí en la creación la fecha donde entra por el DTO los datos, y ya no se modifica!
        );
    }
}


