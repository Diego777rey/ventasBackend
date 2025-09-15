package py.edu.facitec.ventas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.facitec.ventas.entity.Categoria;
import py.edu.facitec.ventas.entity.Vendedor;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor,Integer> {
   Optional<Vendedor> findByNombre(String nombre);
    Page<Vendedor> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
