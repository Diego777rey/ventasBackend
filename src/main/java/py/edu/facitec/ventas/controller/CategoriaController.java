package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.Categoria;
import py.edu.facitec.ventas.service.CategoriaService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // ==== Queries ====
    @QueryMapping
    public List<Categoria> findAllCategorias() {
        return categoriaService.findAllCategorias();
    }

    @QueryMapping
    public Categoria findCategoriaById(@Argument("categoriaId") int id) {
        return categoriaService.findOneCategoria(id);
    }
    @QueryMapping
    public PaginadorDto<Categoria> findCategoriasPaginated(
            @Argument("page") int page,
            @Argument("size") int size,
            @Argument("search") String search
    ) {
        return categoriaService.findCategoriasPaginated(page, size, search);
    }


    // ==== Mutations (adaptados al schema) ====
    @MutationMapping
    public Categoria saveCategoria(@Argument("inputCategoria") Categoria inputCategoria) {
        return categoriaService.saveCategoria(inputCategoria);
    }

    @MutationMapping
    public Categoria updateCategoria(@Argument("id") int id,
                                     @Argument("inputCategoria") Categoria inputCategoria) {
        return categoriaService.updateCategoria(id, inputCategoria);
    }

    @MutationMapping
    public Categoria deleteCategoria(@Argument("id") int id) {
        return categoriaService.deleteCategoria(id);
    }

    // ==== Subscriptions ====
    @SubscriptionMapping
    public Flux<Categoria> findAllCategoriasFlux() {
        return categoriaService.findAllCategoriaesFlux();
    }

    @SubscriptionMapping
    public Mono<Categoria> findOneCategoriaMono(@Argument("id") int id) {
        return categoriaService.findOneMono(id);
    }
}
