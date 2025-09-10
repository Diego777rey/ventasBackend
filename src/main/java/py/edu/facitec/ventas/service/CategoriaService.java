package py.edu.facitec.ventas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.entity.Categoria;
import py.edu.facitec.ventas.repository.CategoriaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
@Service
@Slf4j
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;
    public List<Categoria> findAllCategorias() {
        return categoriaRepository.findAll();
    }
    public Categoria findOneCategoria(int id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no existe"));
    }
    public Flux<Categoria> findAllCategoriaesFlux() {
        return Flux.fromIterable(findAllCategorias())
                .delayElements(Duration.ofSeconds(1)) // emite uno cada segundo
                .take(10);
    }
    public Mono<Categoria> findOneMono(int id) {
        return Mono.justOrEmpty(categoriaRepository.findById(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Categoria con id " + id + " no existe")));
    }
    public Categoria saveCategoria(Categoria dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Categoria no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Categoria categoria = Categoria.builder()
                .descripcion(dto.getDescripcion())
                .build();

        return categoriaRepository.save(categoria);
    }
    public Categoria updateCategoria(int id, Categoria dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Categoria no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Categoria Categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no existe"));

        Categoria.setDescripcion(dto.getDescripcion());

        return categoriaRepository.save(Categoria);
    }
    public Categoria deleteCategoria(int id) {
        Categoria Categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no existe"));

        categoriaRepository.delete(Categoria);
        return Categoria;
    }
    private void validarCamposObligatorios(Categoria dto) {
        if (dto.getDescripcion() == null || dto.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
    }
}