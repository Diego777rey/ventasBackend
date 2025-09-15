/*podemos usar esta clase como un resolver personalizado
package py.edu.facitec.ventas.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.entity.Venta;
import py.edu.facitec.ventas.entity.VentaDetalle;
import py.edu.facitec.ventas.service.VentaDetalleService;

import java.util.List;

@Controller
public class VentaDetalleResolver {

    @Autowired
    private VentaDetalleService ventaDetalleService;

    @SchemaMapping(typeName = "Venta", field = "items")
    public List<VentaDetalle> getItems(Venta venta) {
        return ventaDetalleService.findByVentaId(venta.getId());
    }
}
*/