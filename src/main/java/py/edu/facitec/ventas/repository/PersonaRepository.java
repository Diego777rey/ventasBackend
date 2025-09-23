/*package py.edu.facitec.ventas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.facitec.ventas.entity.Persona;
import py.edu.facitec.ventas.enums.RolPersona;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    Optional<Persona> findByDocumento(String documento);
    Optional<Persona> findByEmail(String email);

    // Consulta por rol
    List<Persona> findByRolesContaining(RolPersona rol);

    // Paginación con búsqueda en nombre o apellido
    Page<Persona> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            String nombre, String apellido, Pageable pageable);
}*/