package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.dto.InputUsuario;
import py.edu.facitec.ventas.entity.Usuario;
import py.edu.facitec.ventas.service.UsuarioService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @QueryMapping
    public List<Usuario> findAllUsuarios() {
        return usuarioService.findAllUsuarios();
    }

    @QueryMapping
    public Usuario findUsuarioById(@Argument("UsuarioId") Long UsuarioId) {
        return usuarioService.findOneUsuario(UsuarioId);
    }
    @QueryMapping
    public Usuario findOneUsuario(@Argument("id") Long id) {
        return usuarioService.findOneUsuario(id);
    }
    @MutationMapping
    public Usuario createUsuario(@Argument("inputUsuario") InputUsuario inputUsuario) {
        return usuarioService.saveUsuario(inputUsuario);
    }
    @MutationMapping
    public Usuario saveUsuario(@Argument("dto") InputUsuario dto) {
        return usuarioService.saveUsuario(dto);
    }
    @MutationMapping
    public Usuario updateUsuario(@Argument("id") Long id, @Argument("inputUsuario") InputUsuario inputUsuario) {
        return usuarioService.updateUsuario(id, inputUsuario);
    }
    @MutationMapping
    public Usuario updateUsuarioWithId(@Argument("id") Long id, @Argument("dto") InputUsuario dto) {
        return usuarioService.updateUsuario(id, dto);
    }
    @MutationMapping
    public Usuario deleteUsuario(@Argument("id") Long id) {
        return usuarioService.deleteUsuario(id);
    }
    @MutationMapping
    public String deleteUsuarioById(@Argument("UsuarioId") Long UsuarioId) {
        usuarioService.deleteUsuario(UsuarioId);
        return "Usuario eliminado exitosamente";
    }
    @SubscriptionMapping
    public Flux<Usuario> findAllUsuariosFlux() {
        return usuarioService.findAllUsuariosFlux();
    }
    @SubscriptionMapping
    public Mono<Usuario> findOneUsuarioMono(@Argument("id") Long id) {
        return usuarioService.findOneMono(id);
    }
}