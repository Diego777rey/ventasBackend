package py.edu.facitec.ventas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.dto.InputProducto;
import py.edu.facitec.ventas.entity.Categoria;
import py.edu.facitec.ventas.entity.Producto;
import py.edu.facitec.ventas.repository.CategoriaRepository;
import py.edu.facitec.ventas.repository.ProductoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    public List<Producto> findAllProductos() {
        return productoRepository.findAll();
    }

    public Producto findOneProducto(int id) {
        return productoRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Producto con id " + id + " no existe"));
    }

    public Flux<Producto> findAllProductoesFlux() {
        return Flux.fromIterable(findAllProductos())
                .delayElements(Duration.ofSeconds(1))
                .take(10);
    }

    public Mono<Producto> findOneMono(int id) {
        return Mono.justOrEmpty(productoRepository.findById((int) id))
                .switchIfEmpty(Mono.error(new RuntimeException("Producto con id " + id + " no existe")));
    }
    public Producto saveProducto(InputProducto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Producto no puede ser nulo");
        }

        validarCamposObligatorios(dto);
        Categoria categoria = categoriaRepository.findById((int) dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id " + dto.getCategoriaId()));
        Producto producto = Producto.builder()
                .descripcion(dto.getDescripcion())
                .precioCompra(dto.getPrecioCompra())
                .precioVenta(dto.getPrecioVenta())
                .stock(dto.getStock())
                .activo(dto.getActivo())
                .categoria(categoria)
                .build();

        return productoRepository.save(producto);
    }
    public Producto updateProducto(int id, InputProducto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Producto no puede ser nulo");
        }

        validarCamposObligatorios(dto);

        Producto producto = productoRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Producto con id " + id + " no existe"));

        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecioCompra(dto.getPrecioCompra());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setStock(dto.getStock());
        producto.setActivo(dto.getActivo());
        Categoria categoria = categoriaRepository.findById((int) dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id " + dto.getCategoriaId()));
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }
    public Producto deleteProducto(int id) {
        Producto producto = productoRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Producto con id " + id + " no existe"));

        productoRepository.delete(producto);
        return producto;
    }
    private void validarCamposObligatorios(InputProducto dto) {
        if (dto.getDescripcion() == null || dto.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
    }
}
