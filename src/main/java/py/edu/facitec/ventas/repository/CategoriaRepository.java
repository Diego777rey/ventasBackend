package py.edu.facitec.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.facitec.ventas.entity.Categoria;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {
    Optional<Categoria> findByDescripcion(String descripcion);
}
