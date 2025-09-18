package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.dto.AuthPayload;
import py.edu.facitec.ventas.dto.InputUsuario;
import py.edu.facitec.ventas.dto.LoginInput;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.Usuario;
import py.edu.facitec.ventas.service.UsuarioService;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtProvider jwtProvider;

    // ==== Queries ====

    @QueryMapping(name = "findAllUsuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> findAllUsuarios() {
        return usuarioService.findAllUsuarios();
    }

    @QueryMapping(name = "findUsuarioById")
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario findUsuarioById(@Argument("id") Long id) {
        return usuarioService.findOneUsuario(id);
    }

    @QueryMapping(name = "findUsuariosPaginated")
    @PreAuthorize("hasRole('ADMIN')")
    public PaginadorDto<Usuario> findUsuariosPaginated(
            @Argument("page") int page,
            @Argument("size") int size,
            @Argument("search") String search
    ) {
        return usuarioService.findUsuariosPaginated(page, size, search);
    }

    @QueryMapping(name = "findUsuarioByEmail")
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario findUsuarioByEmail(@Argument("email") String email) {
        return usuarioService.findUsuarioByEmail(email);
    }

    // ==== Mutations ====

    @MutationMapping(name = "createUsuario")
    //@PreAuthorize("hasRole('ADMIN')")
    public Usuario createUsuario(@Argument("inputUsuario") InputUsuario inputUsuario) {
        return usuarioService.saveUsuario(inputUsuario);
    }

    @MutationMapping(name = "updateUsuario")
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario updateUsuario(@Argument("id") Long id,
                                 @Argument("inputUsuario") InputUsuario inputUsuario) {
        return usuarioService.updateUsuario(id, inputUsuario);
    }

    @MutationMapping(name = "deleteUsuario")
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario deleteUsuario(@Argument("id") Long id) {
        return usuarioService.deleteUsuario(id);
    }

    // ==== Login ====

    @MutationMapping
    public AuthPayload login(@Argument("input") LoginInput input) {
        return usuarioService.login(input.getNombre(), input.getContrasenha())
                .map(usuario -> new AuthPayload(usuario, jwtProvider.generateToken(usuario), null))
                .orElseGet(() -> new AuthPayload(null, null, "Nombre de usuario o contraseña incorrectos"));
    }

}
