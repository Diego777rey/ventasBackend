package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.entity.Vendedor;
import py.edu.facitec.ventas.service.VendedorService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Controller
public class VendedorController {

    @Autowired
    private VendedorService VendedorService;
    @QueryMapping
    public List<Vendedor> findAllVendedores() {
        return VendedorService.findAllVendedores();
    }
    @QueryMapping
    public Vendedor findVendedorById(@Argument("VendedorId") int VendedorId) {
        return VendedorService.findOneVendedor(VendedorId);
    }
    @QueryMapping
    public Vendedor findOneVendedor(@Argument("id") int id) {
        return VendedorService.findOneVendedor(id);
    }
    @MutationMapping
    public Vendedor createVendedor(@Argument("inputVendedor") Vendedor inputVendedor) {
        return VendedorService.saveVendedor(inputVendedor);
    }

    @MutationMapping
    public Vendedor saveVendedor(@Argument("dto") Vendedor dto) {
        return VendedorService.saveVendedor(dto);
    }

    @MutationMapping
    public Vendedor updateVendedor(@Argument("id") int id, @Argument("inputVendedor") Vendedor inputVendedor) {
        return VendedorService.updateVendedor(id, inputVendedor);
    }

    @MutationMapping
    public Vendedor updateVendedorWithId(@Argument("id") int id, @Argument("dto") Vendedor dto) {
        return VendedorService.updateVendedor(id, dto);
    }

    @MutationMapping
    public Vendedor deleteVendedor(@Argument("id") int id) {
        return VendedorService.deleteVendedor(id);
    }

    @MutationMapping
    public String deleteVendedorById(@Argument("VendedorId") int VendedorId) {
        VendedorService.deleteVendedor(VendedorId);
        return "Vendedor eliminado exitosamente";
    }
    @SubscriptionMapping
    public Flux<Vendedor> findAllVendedoresFlux() {
        return VendedorService.findAllVendedoresFlux();
    }
    @SubscriptionMapping
    public Mono<Vendedor> findOneVendedorMono(@Argument("id") int id) {
        return VendedorService.findOneMono(id);
    }
}