package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.dto.InputUsuario;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.Cliente;
import py.edu.facitec.ventas.entity.Usuario;
import py.edu.facitec.ventas.service.UsuarioService;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ==== Queries ====
    @QueryMapping
    public List<Usuario> findAllUsuarios() {
        return usuarioService.findAllUsuarios();
    }

    @QueryMapping
    public Usuario findUsuarioById(@Argument("id") Long id) {
        return usuarioService.findOneUsuario(id);
    }

    @QueryMapping
    public PaginadorDto<Usuario> findUsuariosPaginated(
            @Argument("page") int page,
            @Argument("size") int size,
            @Argument("search") String search
    ) {
        return usuarioService.findUsuariosPaginated(page, size, search);
    }

    // ==== Mutations ====
    @MutationMapping
    public Usuario createUsuario(@Argument("inputUsuario") InputUsuario inputUsuario) {
        return usuarioService.saveUsuario(inputUsuario);
    }

    @MutationMapping
    public Usuario updateUsuario(@Argument("id") Long id,
                                 @Argument("inputUsuario") InputUsuario inputUsuario) {
        return usuarioService.updateUsuario(id, inputUsuario);
    }

    @MutationMapping
    public Usuario deleteUsuario(@Argument("id") Long id) {
        return usuarioService.deleteUsuario(id);
    }
}
