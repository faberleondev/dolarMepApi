package online.gamelogy.dolarMepApi.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import online.gamelogy.dolarMepApi.config.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DatosRespuestaCotizacion(Long id,
                                       String entidad,
                                       BigDecimal precioCompra,
                                       BigDecimal precioVenta,
                                       @JsonSerialize(using = LocalDateTimeSerializer.class)
                                       LocalDateTime fechaActualizacion
) {}

