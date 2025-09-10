package py.edu.facitec.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.facitec.ventas.entity.Persona;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona,Integer> {
    //Optional<Persona> findByNombre(String nombre);
}
