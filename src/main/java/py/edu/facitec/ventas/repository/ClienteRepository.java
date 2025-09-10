package py.edu.facitec.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.facitec.ventas.entity.Cliente;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    Optional<Cliente> findByNombre(String nombre);
}
