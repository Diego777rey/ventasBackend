package py.edu.facitec.ventas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import py.edu.facitec.ventas.entity.Categoria;
import py.edu.facitec.ventas.entity.Cliente;
import py.edu.facitec.ventas.entity.Vendedor;

import java.util.List;
import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor,Integer> {
    Optional<Vendedor> findByNombre(String nombre);
    Page<Vendedor> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    @Query("SELECT c FROM Vendedor c " +
            "WHERE (:nombre IS NULL OR c.nombre LIKE %:nombre%) " +//aca realizamos una consulta y filtramos por nombre
            "AND (:fechaInicio IS NULL OR c.fechaNacimiento >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR c.fechaNacimiento <= :fechaFin)")
    List<Vendedor> findByFiltros(String nombre, String fechaInicio, String fechaFin);
}