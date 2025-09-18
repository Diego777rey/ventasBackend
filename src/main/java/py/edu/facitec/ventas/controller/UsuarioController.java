package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.dto.InputUsuario;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.Usuario;
import py.edu.facitec.ventas.service.UsuarioService;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ==== Queries ====

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> findAllUsuarios() {
        return usuarioService.findAllUsuarios();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN') or principal.username == #email")
    public Usuario findUsuarioById(@Argument("id") Long id) {
        return usuarioService.findOneUsuario(id);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PaginadorDto<Usuario> findUsuariosPaginated(
            @Argument("page") int page,
            @Argument("size") int size,
            @Argument("search") String search
    ) {
        return usuarioService.findUsuariosPaginated(page, size, search);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN') or principal.username == #email")
    public Usuario findUsuarioByEmail(@Argument("email") String email) {
        return usuarioService.findUsuarioByEmail(email);
    }

    // ==== Mutations ====

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario createUsuario(@Argument("inputUsuario") InputUsuario inputUsuario) {
        return usuarioService.saveUsuario(inputUsuario);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN') or principal.username == #email")
    public Usuario updateUsuario(@Argument("id") Long id,
                                 @Argument("inputUsuario") InputUsuario inputUsuario) {
        return usuarioService.updateUsuario(id, inputUsuario);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario deleteUsuario(@Argument("id") Long id) {
        return usuarioService.deleteUsuario(id);
    }

    // ==== Login ====

    @MutationMapping
    public Usuario login(@Argument("nombre") String nombre, @Argument("contrasenha") String contrasenha) {
        return usuarioService.login(nombre, contrasenha)
                .orElseThrow(() -> new RuntimeException("Nombre de usuario o contraseña incorrectos"));
    }
}