package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.entity.VentaDetalle;
import py.edu.facitec.ventas.service.VentaDetalleService;

import java.math.BigDecimal;

@Controller
class VentaResolver {

    @Autowired
    VentaDetalleService ventaDetalleService;

    @SchemaMapping(typeName = "VentaDetalle", field = "subtotal")
    public BigDecimal subtotal(VentaDetalle detalle) {
        return BigDecimal.valueOf(detalle.getPrecio() * detalle.getCantidad());
    }

    @SchemaMapping(typeName = "Venta", field = "items")
    public java.util.List<VentaDetalle> items(py.edu.facitec.ventas.entity.Venta venta) {
        return ventaDetalleService.findByVentaId(venta.getId());
    }
}