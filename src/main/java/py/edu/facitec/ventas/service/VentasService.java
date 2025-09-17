package py.edu.facitec.ventas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.facitec.ventas.dto.InputVenta;
import py.edu.facitec.ventas.dto.InputVentaDetalle;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.*;
import py.edu.facitec.ventas.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentasService {

    private final VentaRepository ventaRepository;
    private final VentaDetalleRepository detalleRepository;
    private final ClienteRepository clienteRepository;
    private final VendedorRepository vendedorRepository;
    private final ProductoRepository productoRepository;

    // ---------------------- CREAR VENTA ----------------------
    @Transactional
    public Venta crearVenta(InputVenta input) {
        Cliente cliente = clienteRepository.findById(input.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Vendedor vendedor = vendedorRepository.findById(input.getVendedorId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        Venta venta = Venta.builder()
                .fecha(input.getFecha())
                .tipoPago(input.getTipoPago())
                .cliente(cliente)
                .vendedor(vendedor)
                .build();

        venta = ventaRepository.save(venta);

        List<VentaDetalle> detalles = new ArrayList<>();
        for (InputVentaDetalle d : input.getItems()) {
            Producto producto = productoRepository.findById(d.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            VentaDetalle detalle = VentaDetalle.builder()
                    .venta(venta)
                    .producto(producto)
                    .cantidad(d.getCantidad())
                    .precio(d.getPrecio())
                    .subtotal(d.getCantidad() * d.getPrecio())
                    .build();

            detalles.add(detalle);
        }
        detalleRepository.saveAll(detalles);

        return venta;
    }

    // ---------------------- ACTUALIZAR VENTA ----------------------
    @Transactional
    public Venta actualizarVenta(Integer id, InputVenta input) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        Cliente cliente = clienteRepository.findById(input.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Vendedor vendedor = vendedorRepository.findById(input.getVendedorId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        venta.setFecha(input.getFecha());
        venta.setTipoPago(input.getTipoPago());
        venta.setCliente(cliente);
        venta.setVendedor(vendedor);
        venta = ventaRepository.save(venta);

        detalleRepository.deleteByVentaId(venta.getId());

        List<VentaDetalle> detalles = new ArrayList<>();
        for (InputVentaDetalle d : input.getItems()) {
            Producto producto = productoRepository.findById(d.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            VentaDetalle detalle = VentaDetalle.builder()
                    .venta(venta)
                    .producto(producto)
                    .cantidad(d.getCantidad())
                    .precio(d.getPrecio())
                    .subtotal(d.getCantidad() * d.getPrecio())
                    .build();

            detalles.add(detalle);
        }
        detalleRepository.saveAll(detalles);

        return venta;
    }

    // ---------------------- ELIMINAR VENTA ----------------------
    @Transactional
    public boolean eliminarVenta(Integer id) {
        if (!ventaRepository.existsById(id)) return false;
        detalleRepository.deleteByVentaId(id);
        ventaRepository.deleteById(id);
        return true;
    }

    // ---------------------- OBTENER VENTA INDIVIDUAL ----------------------
    public Venta obtenerVenta(Integer id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    // ---------------------- PAGINACIÓN ----------------------
    public PaginadorDto<Venta> obtenerVentasPaginadas(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Venta> ventaPage;

        if (search != null && !search.trim().isEmpty()) {
            ventaPage = ventaRepository.buscarPorClienteOVendedor(search, pageable);
        } else {
            ventaPage = ventaRepository.findAll(pageable);
        }

        PaginadorDto<Venta> paginador = new PaginadorDto<>();
        paginador.setItems(ventaPage.getContent());
        paginador.setTotalItems(ventaPage.getTotalElements());
        paginador.setTotalPages(ventaPage.getTotalPages());
        paginador.setCurrentPage(page);
        paginador.setItemsCount(ventaPage.getNumberOfElements());

        return paginador;
    }

    // ---------------------- OBTENER TODAS LAS VENTAS ----------------------
    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }
}
