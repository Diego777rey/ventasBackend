package py.edu.facitec.ventas.dto;

import lombok.Data;

@Data
public class InputVentaDetalle {
    private Integer productoId;
    private int cantidad;
    private Float precio;
    private  Float subTotal;
}