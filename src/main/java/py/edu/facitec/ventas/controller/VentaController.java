package py.edu.facitec.ventas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.dto.InputVenta;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.Cliente;
import py.edu.facitec.ventas.entity.Vendedor;
import py.edu.facitec.ventas.entity.Venta;
import py.edu.facitec.ventas.entity.VentaDetalle;
import py.edu.facitec.ventas.service.VentaDetalleService;
import py.edu.facitec.ventas.service.VentasService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VentaController {

    private final VentasService ventasService;
    private final VentaDetalleService ventaDetalleService;

    // ------------------ MUTATIONS ------------------
    @MutationMapping
    public Venta crearVenta(@Argument InputVenta input) {
        return ventasService.crearVenta(input);
    }

    @MutationMapping
    public Venta actualizarVenta(@Argument Integer id, @Argument InputVenta input) {
        return ventasService.actualizarVenta(id, input);
    }

    @MutationMapping
    public Boolean eliminarVenta(@Argument Integer id) {
        return ventasService.eliminarVenta(id);
    }

    // ------------------ QUERIES ------------------
    @QueryMapping
    public Venta venta(@Argument Integer id) {
        Venta venta = ventasService.obtenerVenta(id);
        // Cargar detalles para que items devuelva datos
        ventaDetalleService.loadDetalles(List.of(venta));
        return venta;
    }

    @QueryMapping
    public List<Venta> ventas() {
        List<Venta> ventas = ventasService.obtenerTodasLasVentas();
        if (!ventas.isEmpty()) {
            ventaDetalleService.loadDetalles(ventas);
        }
        return ventas;
    }

    @QueryMapping
    public PaginadorDto<Venta> findVentasPaginated(@Argument String search,
                                                   @Argument int page,
                                                   @Argument int size) {
        PaginadorDto<Venta> paginador = ventasService.obtenerVentasPaginadas(search, page, size);
        List<Venta> ventas = paginador.getItems();
        if (!ventas.isEmpty()) {
            ventaDetalleService.loadDetalles(ventas);
        }
        return paginador;
    }

    // ------------------ SCHEMA MAPPINGS ------------------
    @SchemaMapping(typeName = "Venta", field = "items")
    public List<VentaDetalle> getItems(Venta venta) {
        return ventaDetalleService.findByVentaId(venta.getId());
    }

    @SchemaMapping(typeName = "Venta", field = "cliente")
    public Cliente getCliente(Venta venta) {
        return venta.getCliente();
    }

    @SchemaMapping(typeName = "Venta", field = "vendedor")
    public Vendedor getVendedor(Venta venta) {
        return venta.getVendedor();
    }
}
