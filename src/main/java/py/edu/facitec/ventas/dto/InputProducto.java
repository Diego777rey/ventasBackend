package py.edu.facitec.ventas.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InputProducto {
    private String descripcion;
    private Double precioCompra;
    private Double precioVenta;
    private Integer stock;
    private Boolean activo;
    private int categoriaId;
}
