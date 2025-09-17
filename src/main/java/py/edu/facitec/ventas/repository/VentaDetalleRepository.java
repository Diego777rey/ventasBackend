package py.edu.facitec.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import py.edu.facitec.ventas.entity.VentaDetalle;

import java.util.List;

@Repository
public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Integer> {

    // ------------------ MÉTODOS ORIGINALES ------------------

    // Trae todos los detalles de una venta
    List<VentaDetalle> findByVentaId(Integer ventaId);

    // Elimina todos los detalles de una venta (usado al actualizar o borrar)
    @Transactional
    @Modifying
    @Query("DELETE FROM VentaDetalle vd WHERE vd.venta.id = :ventaId")
    void deleteByVentaId(@Param("ventaId") Integer ventaId);

    // Trae los detalles de varias ventas por sus IDs
    List<VentaDetalle> findByVentaIdIn(List<Integer> ventaIds);

    // ------------------ NUEVO MÉTODO PARA BATCH LOAD SIN N+1 ------------------

    /**
     * Trae todos los detalles de varias ventas incluyendo Producto y Categoria
     * en un solo query, evitando el problema de N+1.
     *
     * Usar en el servicio de batch (loadDetalles) cuando se devuelven items en GraphQL.
     */
    @Query("SELECT vd FROM VentaDetalle vd " +
            "JOIN FETCH vd.producto p " +
            "JOIN FETCH p.categoria " +
            "WHERE vd.venta.id IN :ventaIds")
    List<VentaDetalle> findByVentaIdsWithProductoYCategoria(@Param("ventaIds") List<Integer> ventaIds);
}
