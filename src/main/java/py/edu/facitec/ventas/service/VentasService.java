package py.edu.facitec.ventas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.facitec.ventas.dto.InputVenta;
import py.edu.facitec.ventas.dto.InputVentaDetalle;
import py.edu.facitec.ventas.entity.*;
import py.edu.facitec.ventas.repository.*;

import java.math.BigDecimal;
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
        validarVenta(dto);

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + dto.getClienteId()));

        Vendedor vendedor = vendedorRepository.findById(dto.getVendedorId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado con ID: " + dto.getVendedorId()));

        Venta venta = Venta.builder()
                .fecha(dto.getFecha())
                .tipoPago(dto.getTipoPago())
                .cliente(cliente)
                .vendedor(vendedor)
                .build();

        Venta ventaGuardada = ventaRepository.save(venta);
        procesarItemsVenta(dto.getItems(), ventaGuardada);

        return ventaGuardada;
    }

    private void validarVenta(InputVenta dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos de la venta no pueden ser nulos");
        }

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un item");
        }

        if (dto.getTipoPago() == null ||
                (!dto.getTipoPago().equals("EFECTIVO") && !dto.getTipoPago().equals("CREDITO"))) {
            throw new IllegalArgumentException("Tipo de pago debe ser EFECTIVO o CREDITO");
        }
        if ("CREDITO".equals(dto.getTipoPago())) {
            validarLimiteCredito(dto.getClienteId(), dto.getItems());
        }
    }

    private void procesarItemsVenta(List<InputVentaDetalle> items, Venta venta) {
        for (InputVentaDetalle detalleDto : items) {
            Producto producto = productoRepository.findById(detalleDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDto.getProductoId()));

            validarStockProducto(producto, detalleDto.getCantidad());

            Double precio = detalleDto.getPrecio() != null ?
                    detalleDto.getPrecio() : producto.getPrecioVenta();

            VentaDetalle detalle = VentaDetalle.builder()
                    .cantidad(detalleDto.getCantidad())
                    .precio(precio)
                    .producto(producto)
                    .venta(venta)
                    .build();

            ventaDetalleRepository.save(detalle);
            producto.setStock(producto.getStock() - detalleDto.getCantidad());
            productoRepository.save(producto);
        }
    }

    private void validarStockProducto(Producto producto, int cantidad) {
        if (producto.getStock() == null || producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getDescripcion() +
                    ". Stock disponible: " + producto.getStock());
        }
    }

    private void validarLimiteCredito(Integer clienteId, List<InputVentaDetalle> items) {
        log.info("Validando límite de crédito para cliente ID: {}", clienteId);
        BigDecimal totalVenta = BigDecimal.ZERO;
        for (InputVentaDetalle item : items) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            Double precio = item.getPrecio() != null ?
                    item.getPrecio() : producto.getPrecioVenta();

            totalVenta = totalVenta.add(BigDecimal.valueOf(precio * item.getCantidad()));
        }
    }

    @Transactional
    public void deleteVenta(int id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        List<VentaDetalle> detalles = ventaDetalleRepository.findByVentaId(venta.getId());

        for (VentaDetalle detalle : detalles) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
            ventaDetalleRepository.delete(detalle); //aca eliminamos el detalle de la venta
        }

        ventaRepository.delete(venta);
    }


    @Transactional
    public Venta updateVenta(int id, InputVenta dto) {
        deleteVenta(id);
        return saveVenta(dto);
    }
}