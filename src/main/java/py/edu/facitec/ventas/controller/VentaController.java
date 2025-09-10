package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.dto.InputVenta;
import py.edu.facitec.ventas.entity.Venta;
import py.edu.facitec.ventas.service.VentasService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class VentaController {
    @Autowired
    VentasService ventasService;

    @QueryMapping
    public List<Venta> findAllVentas() {
        return ventasService.findAllVentas();
    }

    @QueryMapping
    public Venta findVentaById(@Argument("VentaId") int VentaId) {
        return ventasService.findOneVenta(VentaId);
    }

    @QueryMapping
    public Venta findOneVenta(@Argument("id") int id) {
        return ventasService.findOneVenta(id);
    }

    @MutationMapping
    public Venta createVenta(@Argument("inputVenta") InputVenta inputVenta) {
        return ventasService.saveVenta(inputVenta);
    }

    @MutationMapping
    public Venta saveVenta(@Argument("dto") InputVenta dto) {
        return ventasService.saveVenta(dto);
    }

    @MutationMapping
    public Venta updateVenta(@Argument("id") int id, @Argument("inputVenta") InputVenta inputVenta) {
        return ventasService.updateVenta(id, inputVenta);
    }

    @MutationMapping
    public Venta updateVentaWithId(@Argument("id") int id, @Argument("dto") InputVenta dto) {
        return ventasService.updateVenta(id, dto);
    }


    @MutationMapping
    public Venta deleteVenta(@Argument("id") int id) {
        ventasService.deleteVenta(id);
        return null; // Devuelve null en lugar de buscar la venta eliminada
    }

    @MutationMapping
    public String deleteVentaById(@Argument("VentaId") int VentaId) {
        ventasService.deleteVenta(VentaId);
        return "Venta eliminada exitosamente";
    }

    @SubscriptionMapping
    public Flux<Venta> findAllVentasFlux() {
        return ventasService.findAllVentaesFlux();
    }

    @SubscriptionMapping
    public Mono<Venta> findOneVentaMono(@Argument("id") int id) {
        return ventasService.findOneMono(id);
    }
}