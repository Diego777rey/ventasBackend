package py.edu.facitec.ventas.dto;

import lombok.Data;
import java.util.List;

@Data
public class InputVenta {
    private String fecha;
    private String tipoPago;
    private Integer clienteId;
    private Integer vendedorId;
    private List<InputVentaDetalle> items;
}