package py.edu.facitec.ventas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.facitec.ventas.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByEmail(String email);
    Page<Usuario> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
