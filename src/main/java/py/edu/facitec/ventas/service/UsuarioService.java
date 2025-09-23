package py.edu.facitec.ventas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.facitec.ventas.dto.InputUsuario;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.Usuario;
import py.edu.facitec.ventas.enums.Rol;
import py.edu.facitec.ventas.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PaginadorService paginadorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //creamos una lista para retornar todos los usuarios

    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario findOneUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con id " + id + " no existe"));
    }

    public Usuario findUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario con email " + email + " no existe"));
    }

    @Transactional
    public Usuario saveUsuario(InputUsuario dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Usuario no puede ser nulo");
        }

        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con este email");
        }

        validarCamposObligatorios(dto);

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .contrasenha(passwordEncoder.encode(dto.getContrasenha()))
                .email(dto.getEmail())
                .rol(dto.getRol() != null ? dto.getRol() : Rol.USUARIO)
                .build();

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateUsuario(Long id, InputUsuario dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Usuario no puede ser nulo");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con id " + id + " no existe"));

        if (dto.getEmail() != null && !dto.getEmail().equals(usuario.getEmail())) {
            if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Ya existe un usuario con este email");
            }
            usuario.setEmail(dto.getEmail());
        }

        if (dto.getNombre() != null) {
            usuario.setNombre(dto.getNombre());
        }

        if (dto.getContrasenha() != null && !dto.getContrasenha().isEmpty()) {
            usuario.setContrasenha(passwordEncoder.encode(dto.getContrasenha()));
        }

        if (dto.getRol() != null) {
            usuario.setRol(dto.getRol());
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
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
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
    }

    // --- Login con PasswordEncoder ---
    public Optional<Usuario> login(String nombre, String contrasenha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombre(nombre);
        if (usuarioOpt.isPresent() &&
                passwordEncoder.matches(contrasenha, usuarioOpt.get().getContrasenha())) {
            return usuarioOpt;
        }
        return Optional.empty();
    }
}