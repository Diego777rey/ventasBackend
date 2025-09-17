/*package py.edu.facitec.ventas.resolvers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.entity.Venta;
import py.edu.facitec.ventas.entity.VentaDetalle;
import py.edu.facitec.ventas.service.VentaDetalleService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VentaResolver {
    private final VentaDetalleService detalleService;

    @SchemaMapping(typeName = "Venta", field = "items")
    public List<VentaDetalle> items(Venta venta) {
        return detalleService.findByVentaId(venta.getId());
    }
}
*/