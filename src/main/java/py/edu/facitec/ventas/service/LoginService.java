package py.edu.facitec.ventas.service;

import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.entity.Usuario;
import py.edu.facitec.ventas.repository.UsuarioRepository;

@Service
public class LoginService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public LoginService(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public String login(String nombre, String contrasenha) {
        Usuario usuario = usuarioRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getContrasenha().equals(contrasenha)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return jwtService.generateToken(usuario.getNombre(), usuario.getRol().name());
    }
}
