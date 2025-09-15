package py.edu.facitec.ventas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.dto.InputProducto;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.Categoria;
import py.edu.facitec.ventas.entity.Producto;
import py.edu.facitec.ventas.repository.CategoriaRepository;
import py.edu.facitec.ventas.repository.ProductoRepository;

import java.util.List;

@Service
@Slf4j
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PaginadorService paginadorService;

    // ================== CRUD ==================

    public List<Producto> findAllProductos() {
        return productoRepository.findAll(); // ya trae categoría gracias a @EntityGraph
    }

    public Producto findOneProducto(int id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto con id " + id + " no existe"));
    }

    public Producto saveProducto(InputProducto dto) {
        validarCamposObligatorios(dto);
        validarNegocio(dto);

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id " + dto.getCategoriaId()));

        Producto producto = Producto.builder()
                .descripcion(dto.getDescripcion())
                .precioCompra(dto.getPrecioCompra())
                .precioVenta(dto.getPrecioVenta())
                .stock(dto.getStock())
                .activo(dto.getActivo())
                .categoria(categoria)
                .build();

        Producto saved = productoRepository.save(producto);
        log.info("Producto creado: {}", saved.getDescripcion());
        return saved;
    }

    public Producto updateProducto(int id, InputProducto dto) {
        validarCamposObligatorios(dto);
        validarNegocio(dto);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto con id " + id + " no existe"));

        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecioCompra(dto.getPrecioCompra());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setStock(dto.getStock());
        producto.setActivo(dto.getActivo());

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id " + dto.getCategoriaId()));
        producto.setCategoria(categoria);

        Producto updated = productoRepository.save(producto);
        log.info("Producto actualizado: {}", updated.getDescripcion());
        return updated;
    }

    public Producto deleteProducto(int id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto con id " + id + " no existe"));
        productoRepository.delete(producto);
        log.info("Producto eliminado: {}", producto.getDescripcion());
        return producto;
    }

    public PaginadorDto<Producto> findProductosPaginated(int page, int size, String search) {
        return paginadorService.paginarConFiltro(
                (s, pageable) -> {
                    if (s == null || s.trim().isEmpty()) {
                        return productoRepository.findAll(pageable);
                    }
                    return productoRepository.findByDescripcionContainingIgnoreCase(s, pageable);
                },
                search,
                page,
                size
        );
    }

    // ================== VALIDACIONES ==================

    private void validarCamposObligatorios(InputProducto dto) {
        if (dto.getDescripcion() == null || dto.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
    }

    private void validarNegocio(InputProducto dto) {
        if (dto.getStock() != null && dto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (dto.getPrecioCompra() != null && dto.getPrecioVenta() != null
                && dto.getPrecioVenta() < dto.getPrecioCompra()) {
            throw new IllegalArgumentException("El precio de venta no puede ser menor al precio de compra");
        }
    }
}
