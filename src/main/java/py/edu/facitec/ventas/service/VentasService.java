package py.edu.facitec.ventas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.facitec.ventas.dto.InputVenta;
import py.edu.facitec.ventas.dto.InputVentaDetalle;
import py.edu.facitec.ventas.entity.*;
import py.edu.facitec.ventas.repository.ClienteRepository;
import py.edu.facitec.ventas.repository.ProductoRepository;
import py.edu.facitec.ventas.repository.VendedorRepository;
import py.edu.facitec.ventas.repository.VentaRepository;
import py.edu.facitec.ventas.repository.VentaDetalleRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
@Slf4j
public class VentasService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    VendedorRepository vendedorRepository;

    @Autowired
    VentaRepository ventaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    VentaDetalleRepository ventaDetalleRepository;
    public List<Venta> findAllVentas() {
        return ventaRepository.findAll();
    }
    public Venta findOneVenta(int id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta con id " + id + " no existe"));
    }
    @Transactional
    public Venta saveVenta(InputVenta dto) {
        if (dto == null || dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener items");
        }
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + dto.getClienteId()));

        Vendedor vendedor = vendedorRepository.findById(dto.getVendedorId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado con ID: " + dto.getVendedorId()));
        Venta venta = Venta.builder()
                .fecha((dto.getFecha()))
                .tipoPago(dto.getTipoPago())
                .cliente(cliente)
                .vendedor(vendedor)
                .build();
        Venta ventaGuardada = ventaRepository.save(venta);
        for (InputVentaDetalle detalleDto : dto.getItems()) {
            Producto producto = productoRepository.findById(detalleDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDto.getProductoId()));

            if (producto.getStock() == null || producto.getStock() < detalleDto.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getDescripcion());
            }
            VentaDetalle detalle = VentaDetalle.builder()
                    .cantidad(detalleDto.getCantidad())
                    .precio(detalleDto.getPrecio() != null ? detalleDto.getPrecio() : producto.getPrecioVenta())
                    .producto(producto)
                    .venta(ventaGuardada)
                    .build();
            ventaDetalleRepository.save(detalle);
            producto.setStock(producto.getStock() - detalleDto.getCantidad());
            productoRepository.save(producto);
        }

        return ventaGuardada;
    }
    public Flux<Venta> findAllVentaesFlux() {
        return Flux.fromIterable(ventaRepository.findAll());
    }

    public Mono<Venta> findOneMono(int id) {
        return Mono.justOrEmpty(ventaRepository.findById(id));
    }
    @Transactional
    public void deleteVenta(int id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        List<VentaDetalle> detalles = ventaDetalleRepository.findByVentaId(id);
        for (VentaDetalle detalle : detalles) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }
        ventaRepository.delete(venta);
    }
    @Transactional
    public Venta updateVenta(int id, InputVenta dto) {
        deleteVenta(id);
        return saveVenta(dto);
    }
}