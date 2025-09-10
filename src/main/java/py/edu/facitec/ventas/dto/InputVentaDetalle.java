package py.edu.facitec.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.facitec.ventas.entity.Venta;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputVentaDetalle {
    private int cantidad;
    private Double precio;
    private Integer productoId;
    private Venta ventaId;
}
