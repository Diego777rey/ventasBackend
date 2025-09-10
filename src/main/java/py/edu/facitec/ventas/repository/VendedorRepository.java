package py.edu.facitec.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.facitec.ventas.entity.Vendedor;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor,Integer> {
   Optional<Vendedor> findByNombre(String nombre);
}
