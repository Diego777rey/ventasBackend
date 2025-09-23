package py.edu.facitec.ventas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.edu.facitec.ventas.entity.Cliente;

import java.util.List;
import java.util.Optional;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    Optional<Cliente> findByNombre(String nombre);
    Page<Cliente> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    @Query("SELECT c FROM Cliente c " +
            "WHERE (:nombre IS NULL OR c.nombre LIKE %:nombre%) " +//aca realizamos una consulta y filtramos por nombre
            "AND (:fechaInicio IS NULL OR c.fechaRegistro >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR c.fechaRegistro <= :fechaFin)")
    List<Cliente> findByFiltros(String nombre, String fechaInicio, String fechaFin);
}


