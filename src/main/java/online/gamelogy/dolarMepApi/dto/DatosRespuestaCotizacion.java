package online.gamelogy.dolarMepApi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DatosRespuestaCotizacion(Long id,
                                       String entidad,
                                       BigDecimal precioCompra,
                                       BigDecimal precioVenta,
                                       LocalDateTime fechaActualizacion
) {}

