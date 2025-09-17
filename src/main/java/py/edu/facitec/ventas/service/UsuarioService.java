package py.edu.facitec.ventas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.dto.InputUsuario;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.Usuario;
import py.edu.facitec.ventas.entity.Vendedor;
import py.edu.facitec.ventas.repository.UsuarioRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class UsuarioService {
    @Autowired
    PaginadorService paginadorService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario findOneUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con id " + id + " no existe"));
    }

    public Flux<Usuario> findAllUsuariosFlux() {
        return Flux.fromIterable(findAllUsuarios())
                .delayElements(Duration.ofSeconds(1))
                .take(10);
    }

    public Mono<Usuario> findOneMono(Long id) {
        return Mono.justOrEmpty(usuarioRepository.findById(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario con id " + id + " no existe")));
    }

    public Usuario saveUsuario(InputUsuario dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Usuario no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .contrasenha(dto.getContrasenha())
                .build();

        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, InputUsuario dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Usuario no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con id " + id + " no existe"));

        usuario.setNombre(dto.getNombre());
        usuario.setContrasenha(dto.getContrasenha());

        return usuarioRepository.save(usuario);
    }

    public Usuario deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con id " + id + " no existe"));
        usuarioRepository.delete(usuario);
        return usuario;
    }
    public PaginadorDto<Usuario> findUsuariosPaginated(int page, int size, String search) {
        return paginadorService.<Usuario>paginarConFiltro(
                (s, pageable) -> {
                    if (s == null || s.trim().isEmpty()) {
                        return usuarioRepository.findAll(pageable);
                    }
                    return usuarioRepository.findByNombreContainingIgnoreCase(s, pageable);
                },
                search,
                page,
                size
        );
    }




    private void validarCamposObligatorios(InputUsuario dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (dto.getContrasenha() == null || dto.getContrasenha().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
    }
}