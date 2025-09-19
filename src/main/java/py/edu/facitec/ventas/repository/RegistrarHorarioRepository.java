package py.edu.facitec.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.facitec.ventas.entity.RegistrarHorario;

import java.util.List;

public interface RegistrarHorarioRepository extends JpaRepository<RegistrarHorario, Long> {
    List<RegistrarHorario> findByVendedorId(Long vendedorId);
}
