package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.entity.Categoria;
import py.edu.facitec.ventas.service.CategoriaService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    @QueryMapping
    public List<Categoria> findAllCategorias() {
        return categoriaService.findAllCategorias();
    }

    @QueryMapping
    public Categoria findCategoriaById(@Argument("CategoriaId") int CategoriaId) {
        return categoriaService.findOneCategoria(CategoriaId);
    }
    @QueryMapping
    public Categoria findOneCategoria(@Argument("id") int id) {
        return categoriaService.findOneCategoria(id);
    }
    @MutationMapping
    public Categoria createCategoria(@Argument("inputCategoria") Categoria inputCategoria) {
        return categoriaService.saveCategoria(inputCategoria);
    }
    @MutationMapping
    public Categoria saveCategoria(@Argument("dto") Categoria dto) {
        return categoriaService.saveCategoria(dto);
    }
    @MutationMapping
    public Categoria updateCategoria(@Argument("id") int id, @Argument("inputCategoria") Categoria inputCategoria) {
        return categoriaService.updateCategoria(id, inputCategoria);
    }
    @MutationMapping
    public Categoria updateCategoriaWithId(@Argument("id") int id, @Argument("dto") Categoria dto) {
        return categoriaService.updateCategoria(id, dto);
    }
    @MutationMapping
    public Categoria deleteCategoria(@Argument("id") int id) {
        return categoriaService.deleteCategoria(id);
    }
    @MutationMapping
    public String deleteCategoriaById(@Argument("CategoriaId") int CategoriaId) {
        categoriaService.deleteCategoria(CategoriaId);
        return "Categoria eliminado exitosamente";
    }
    @SubscriptionMapping
    public Flux<Categoria> findAllCategoriasFlux() {
        return categoriaService.findAllCategoriaesFlux();
    }
    @SubscriptionMapping
    public Mono<Categoria> findOneCategoriaMono(@Argument("id") int id) {
        return categoriaService.findOneMono(id);
    }
}