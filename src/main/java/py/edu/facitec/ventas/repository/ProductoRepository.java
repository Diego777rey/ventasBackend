package py.edu.facitec.ventas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.facitec.ventas.entity.Producto;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {

    @EntityGraph(attributePaths = "categoria")
    Page<Producto> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "categoria")
    Page<Producto> findByDescripcionContainingIgnoreCase(String descripcion, Pageable pageable);
//definimos la entidades graphql para evitar el error n+1 y asegurar la optimizacion de la aplicacion
    @EntityGraph(attributePaths = "categoria")
    Optional<Producto> findById(Integer id);
}
