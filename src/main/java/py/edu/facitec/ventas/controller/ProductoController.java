package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.dto.InputProducto;
import py.edu.facitec.ventas.entity.Producto;
import py.edu.facitec.ventas.service.ProductoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService ProductoService;
    @QueryMapping
    public List<Producto> findAllProductos() {
        return ProductoService.findAllProductos();
    }
    @QueryMapping
    public Producto findProductoById(@Argument("ProductoId") int ProductoId) {
        return ProductoService.findOneProducto(ProductoId);
    }

    @QueryMapping
    public Producto findOneProducto(@Argument("id") int id) {
        return ProductoService.findOneProducto(id);
    }

    @MutationMapping
    public Producto createProducto(@Argument("inputProducto") InputProducto inputProducto) {
        return ProductoService.saveProducto(inputProducto);
    }
    @MutationMapping
    public Producto saveProducto(@Argument("dto") InputProducto dto) {
        return ProductoService.saveProducto(dto);
    }

    @MutationMapping
    public Producto updateProducto(@Argument("id") int id, @Argument("inputProducto") InputProducto inputProducto) {
        return ProductoService.updateProducto(id, inputProducto);
    }
    @MutationMapping
    public Producto updateProductoWithId(@Argument("id") int id, @Argument("dto") InputProducto dto) {
        return ProductoService.updateProducto(id, dto);
    }
    @MutationMapping
    public Producto deleteProducto(@Argument("id") int id) {
        return ProductoService.deleteProducto(id);
    }
    @MutationMapping
    public String deleteProductoById(@Argument("ProductoId") int ProductoId) {
        ProductoService.deleteProducto(ProductoId);
        return "Producto eliminado exitosamente";
    }
    @SubscriptionMapping
    public Flux<Producto> findAllProductosFlux() {
        return ProductoService.findAllProductoesFlux();
    }
    @SubscriptionMapping
    public Mono<Producto> findOneProductoMono(@Argument("id") int id) {
        return ProductoService.findOneMono(id);
    }
}