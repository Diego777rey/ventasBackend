package py.edu.facitec.ventas.dto;

import lombok.Data;

@Data
public class InputVentaDetalle {
    private Integer productoId;
    private Integer cantidad;
    private Float precio;
}