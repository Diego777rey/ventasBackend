package py.edu.facitec.ventas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.entity.Venta;
import py.edu.facitec.ventas.entity.VentaDetalle;
import py.edu.facitec.ventas.repository.VentaDetalleRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VentaDetalleService {

    private final VentaDetalleRepository detalleRepository;

    // Cache temporal de detalles para batch
    private final ThreadLocal<Map<Integer, List<VentaDetalle>>> detallesCache = ThreadLocal.withInitial(HashMap::new);

    // Cargar los detalles de todas las ventas de la página (con producto y categoría)
    public void loadDetalles(List<Venta> ventas) {
        List<Integer> ventaIds = ventas.stream().map(Venta::getId).toList();

        // Traer todos los detalles con JOIN FETCH de Producto y Categoria
        List<VentaDetalle> todosDetalles = detalleRepository.findByVentaIdsWithProductoYCategoria(ventaIds);

        Map<Integer, List<VentaDetalle>> map = new HashMap<>();
        for (VentaDetalle vd : todosDetalles) {
            map.computeIfAbsent(vd.getVenta().getId(), k -> new ArrayList<>()).add(vd);
        }

        detallesCache.get().putAll(map);
    }

    // Resolver items por venta
    public List<VentaDetalle> findByVentaId(Integer ventaId) {
        return detallesCache.get().getOrDefault(ventaId, List.of());
    }

    // Limpiar cache después de cada request
    public void clearCache() {
        detallesCache.remove();
    }
}
