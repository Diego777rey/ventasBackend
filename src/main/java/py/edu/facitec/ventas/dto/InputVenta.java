package py.edu.facitec.ventas.dto;

import lombok.Data;
import py.edu.facitec.ventas.enums.TipoPago;

import java.util.List;

@Data
public class InputVenta {
    private String fecha;
    private TipoPago tipoPago;
    private Integer clienteId;
    private Integer vendedorId;
    private List<InputVentaDetalle> items;
}