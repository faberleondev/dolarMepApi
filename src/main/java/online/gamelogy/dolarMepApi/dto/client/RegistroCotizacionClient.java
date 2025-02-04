package online.gamelogy.dolarMepApi.dto.client;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import online.gamelogy.dolarMepApi.config.LocalDateTimeSerializer;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public record RegistroCotizacionClient(
                                 @NotBlank(message = "{nombre.obligatorio}")
                                 String entidad,
                                 @NotNull(message = "{precioCompra.obligatorio}")
                                 BigDecimal precioCompra,
                                 @NotNull(message = "{precioVenta.obligatorio}")
                                 BigDecimal precioVenta,
                                 BigDecimal spread,
                                 BigDecimal porcentajeSpread,
                                 LocalDateTime fechaActualizacion) {
    public RegistroCotizacionClient(String entidad, BigDecimal precioCompra, BigDecimal precioVenta, BigDecimal spread, BigDecimal porcentajeSpread) {
        this(
                entidad,
                precioCompra,
                precioVenta,
                spread,
                porcentajeSpread,
                LocalDateTime.now()// Se asigna aquí en la creación la fecha donde entra por el DTO los datos, y ya no se modifica!
        );
    }
}
//precioVenta.subtract(precioCompra), // CALCULO SPREAD
//                precioCompra.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : precioVenta.subtract(precioCompra).divide(
//                        precioCompra, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)), // porcentaje spread

