package py.edu.facitec.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import py.edu.facitec.ventas.entity.VentaDetalle;

import java.util.List;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Integer> {
    List<VentaDetalle> findByVentaId(int ventaId);

    @Transactional
    @Modifying
    @Query("DELETE FROM VentaDetalle vd WHERE vd.venta.id = :ventaId")
    void deleteByVentaId(@Param("ventaId") int ventaId);
}