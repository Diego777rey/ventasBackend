/*package py.edu.facitec.ventas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.Persona;
import py.edu.facitec.ventas.enums.RolPersona;
import py.edu.facitec.ventas.repository.PersonaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    // === CONSULTAS BÁSICAS ===
    public Persona persona(int id) {
        return personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona con id " + id + " no existe"));
    }

    public List<Persona> personas() {
        return personaRepository.findAll();
    }

    public Persona personaPorDocumento(String documento) {
        return personaRepository.findByDocumento(documento)
                .orElseThrow(() -> new RuntimeException("Persona con documento " + documento + " no existe"));
    }

    public Persona personaPorEmail(String email) {
        return personaRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Persona con email " + email + " no existe"));
    }

    // === CONSULTAS POR ROLES ===
    public List<Persona> personasConRol(RolPersona rol) {
        return personaRepository.findByRolesContaining(rol);
    }

    public List<Persona> personasConRoles(Set<RolPersona> roles) {
        return personaRepository.findByRolesIn(roles);
    }

    public List<Persona> vendedoresQueSonClientes() {
        return personaRepository.findByMultipleRoles(RolPersona.VENDEDOR, RolPersona.CLIENTE);
    }

    // === PAGINACIÓN ===
    public PaginadorDto<Persona> personasPaginated(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Persona> personaPage;
        if (search == null || search.trim().isEmpty()) {
            personaPage = personaRepository.findAll(pageable);
        } else {
            personaPage = personaRepository.findBySearchTerm(search, pageable);
        }

        return PaginadorDto.<Persona>builder()
                .items(personaPage.getContent())
                .totalItems(personaPage.getTotalElements())
                .totalPages(personaPage.getTotalPages())
                .currentPage(page)
                .itemsCount(personaPage.getNumberOfElements())
                .build();
    }

    // === CONSULTAS POR ESTADO ===
    public List<Persona> personasActivas() {
        return personaRepository.findByActivoTrue();
    }

    public List<Persona> personasInactivas() {
        return personaRepository.findByActivoFalse();
    }

    // === ESTADÍSTICAS ===
    public long contarPersonasActivas() {
        return personaRepository.countByActivoTrue();
    }

    public long contarPersonasInactivas() {
        return personaRepository.countByActivoFalse();
    }

    public long contarPersonasConRol(RolPersona rol) {
        return personaRepository.countByRol(rol);
    }

    // === CREACIÓN Y ACTUALIZACIÓN ===
    public Persona crearPersona(Persona persona) {
        validarPersona(persona);
        return personaRepository.save(persona);
    }

    public Persona actualizarPersona(int id, Persona personaActualizada) {
        Persona personaExistente = persona(id);
        personaExistente.setNombre(personaActualizada.getNombre());
        personaExistente.setApellido(personaActualizada.getApellido());
        personaExistente.setDocumento(personaActualizada.getDocumento());
        personaExistente.setTelefono(personaActualizada.getTelefono());
        personaExistente.setEmail(personaActualizada.getEmail());
        personaExistente.setActivo(personaActualizada.getActivo());
        personaExistente.setRoles(personaActualizada.getRoles());
        return personaRepository.save(personaExistente);
    }

    // === ELIMINACIÓN ===
    public Persona eliminarPersona(int id) {
        Persona persona = persona(id);
        personaRepository.delete(persona);
        return persona;
    }

    // === VALIDACIONES ===
    private void validarPersona(Persona persona) {
        if (persona.getNombre() == null || persona.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (persona.getApellido() == null || persona.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }
        if (persona.getDocumento() == null || persona.getDocumento().trim().isEmpty()) {
            throw new IllegalArgumentException("El documento es obligatorio");
        }
    }

    // === REACTIVE STREAMS ===
    public Flux<Persona> personaCreadaStream() {
        return Flux.fromIterable(personas())
                .delayElements(Duration.ofSeconds(2))
                .take(10);
    }

    public Mono<Persona> personaPorIdMono(int id) {
        return Mono.justOrEmpty(personaRepository.findById(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Persona con id " + id + " no existe")));
    }
}*/