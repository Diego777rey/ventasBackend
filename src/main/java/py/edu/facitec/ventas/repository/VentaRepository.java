package py.edu.facitec.ventas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.edu.facitec.ventas.entity.Venta;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    @EntityGraph(attributePaths = {"cliente", "vendedor"})
    Optional<Venta> findById(Integer id);

    @EntityGraph(attributePaths = {"cliente", "vendedor"})
    List<Venta> findAll();

    @EntityGraph(attributePaths = {"cliente", "vendedor"})
    Page<Venta> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"cliente", "vendedor"})
    @Query("SELECT v FROM Venta v WHERE " +
            "LOWER(v.cliente.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(v.vendedor.nombre) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Venta> buscarPorClienteOVendedor(@Param("search") String search, Pageable pageable);
}