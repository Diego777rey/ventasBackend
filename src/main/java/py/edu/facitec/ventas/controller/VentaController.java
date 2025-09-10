package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.dto.InputVenta;
import py.edu.facitec.ventas.dto.InputVentaDetalle;
import py.edu.facitec.ventas.entity.Venta;
import py.edu.facitec.ventas.service.VentasService;

import java.util.List;

@Controller
public class VentaController {

    @Autowired
    VentasService ventasService;

    @QueryMapping
    public List<Venta> ventas() {
        return ventasService.findAllVentas();
    }

    @QueryMapping
    public Venta venta(@Argument Integer id) {
        return ventasService.findOneVenta(id);
    }

    @MutationMapping
    public Venta crearVenta(@Argument VentaInput input) {
        InputVenta inputVenta = convertirVentaInputAInputVenta(input);
        return ventasService.saveVenta(inputVenta);
    }

    @MutationMapping
    public Venta actualizarVenta(@Argument Integer id, @Argument VentaInput input) {
        InputVenta inputVenta = convertirVentaInputAInputVenta(input);
        return ventasService.updateVenta(id, inputVenta);
    }

    @MutationMapping
    public Boolean eliminarVenta(@Argument Integer id) {
        ventasService.deleteVenta(id);
        return true;
    }

    private InputVenta convertirVentaInputAInputVenta(VentaInput input) {
        InputVenta inputVenta = new InputVenta();
        inputVenta.setFecha(input.getFecha());
        inputVenta.setTipoPago(input.getTipoPago());
        inputVenta.setClienteId(Integer.parseInt(input.getClienteId()));
        inputVenta.setVendedorId(Integer.parseInt(input.getVendedorId()));

        // Convertir items
        List<InputVentaDetalle> items = input.getItems().stream()
                .map(itemInput -> {
                    InputVentaDetalle detalle = new InputVentaDetalle();
                    detalle.setProductoId(Integer.parseInt(itemInput.getProductoId()));
                    detalle.setCantidad(itemInput.getCantidad());
                    detalle.setPrecio(itemInput.getPrecio());
                    return detalle;
                })
                .collect(java.util.stream.Collectors.toList());

        inputVenta.setItems(items);

        return inputVenta;
    }
}
class VentaInput {
    private String fecha;
    private String tipoPago;
    private String clienteId;
    private String vendedorId;
    private List<ItemVentaInput> items;

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getTipoPago() { return tipoPago; }
    public void setTipoPago(String tipoPago) { this.tipoPago = tipoPago; }

    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }

    public String getVendedorId() { return vendedorId; }
    public void setVendedorId(String vendedorId) { this.vendedorId = vendedorId; }

    public List<ItemVentaInput> getItems() { return items; }
    public void setItems(List<ItemVentaInput> items) { this.items = items; }
}

class ItemVentaInput {
    private String productoId;
    private int cantidad;
    private Float precio;
    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Float getPrecio() { return precio; }
    public void setPrecio(Float precio) { this.precio = precio; }
}