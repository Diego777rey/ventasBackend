package py.edu.facitec.ventas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import py.edu.facitec.ventas.entity.Categoria;
import py.edu.facitec.ventas.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {
    Optional<Categoria> findByDescripcion(String descripcion);
    Page<Categoria> findByDescripcionContainingIgnoreCase(String descripcion, Pageable pageable);
    @Query("SELECT c FROM Categoria c " +
            "WHERE (:descripcion IS NULL OR c.descripcion LIKE %:descripcion%) ")//aca realizamos una consulta y filtramos por nombre
    List<Categoria> findByFiltros(String descripcion);
}
