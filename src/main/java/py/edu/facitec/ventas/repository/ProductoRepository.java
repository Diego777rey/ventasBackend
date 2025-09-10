package py.edu.facitec.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.facitec.ventas.entity.Producto;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    Optional<Producto> findByDescripcion(String descripcion);
}
