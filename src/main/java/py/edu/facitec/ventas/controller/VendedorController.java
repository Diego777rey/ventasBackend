package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.Categoria;
import py.edu.facitec.ventas.entity.Vendedor;
import py.edu.facitec.ventas.service.VendedorService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Controller
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;
    @QueryMapping
    public List<Vendedor> findAllVendedores() {
        return vendedorService.findAllVendedores();
    }
    @QueryMapping
    public Vendedor findVendedorById(@Argument("VendedorId") int VendedorId) {
        return vendedorService.findOneVendedor(VendedorId);
    }
    @QueryMapping
    public Vendedor findOneVendedor(@Argument("id") int id) {
        return vendedorService.findOneVendedor(id);
    }
    @QueryMapping
    public PaginadorDto<Vendedor> findVendedoresPaginated(
            @Argument("page") int page,
            @Argument("size") int size,
            @Argument("search") String search
    ) {
        return vendedorService.findVendedoresPaginated(page, size, search);
    }



    @MutationMapping
    public Vendedor createVendedor(@Argument("inputVendedor") Vendedor inputVendedor) {
        return vendedorService.saveVendedor(inputVendedor);
    }

    @MutationMapping
    public Vendedor saveVendedor(@Argument("dto") Vendedor dto) {
        return vendedorService.saveVendedor(dto);
    }

    @MutationMapping
    public Vendedor updateVendedor(@Argument("id") int id, @Argument("inputVendedor") Vendedor inputVendedor) {
        return vendedorService.updateVendedor(id, inputVendedor);
    }

    @MutationMapping
    public Vendedor updateVendedorWithId(@Argument("id") int id, @Argument("dto") Vendedor dto) {
        return vendedorService.updateVendedor(id, dto);
    }

    @MutationMapping
    public Vendedor deleteVendedor(@Argument("id") int id) {
        return vendedorService.deleteVendedor(id);
    }

    @MutationMapping
    public String deleteVendedorById(@Argument("VendedorId") int VendedorId) {
        vendedorService.deleteVendedor(VendedorId);
        return "Vendedor eliminado exitosamente";
    }
    @SubscriptionMapping
    public Flux<Vendedor> findAllVendedoresFlux() {
        return vendedorService.findAllVendedoresFlux();
    }
    @SubscriptionMapping
    public Mono<Vendedor> findOneVendedorMono(@Argument("id") int id) {
        return vendedorService.findOneMono(id);
    }
}