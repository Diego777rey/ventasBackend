package py.edu.facitec.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.facitec.ventas.entity.Venta;

import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta,Integer> {
    Optional<Venta> findByTipoPago(String tipoPago);
}
