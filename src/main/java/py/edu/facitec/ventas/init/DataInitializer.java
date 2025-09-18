package py.edu.facitec.ventas.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import py.edu.facitec.ventas.entity.Usuario;
import py.edu.facitec.ventas.enums.Rol;
import py.edu.facitec.ventas.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear usuario admin si no existe
        if (usuarioRepository.findByEmail("admin@example.com").isEmpty()) {
            Usuario admin = Usuario.builder()
                    .nombre("Administrador")
                    .email("admin@example.com")
                    .contrasenha(passwordEncoder.encode("admin123"))
                    .rol(Rol.ADMIN)
                    .build();
            usuarioRepository.save(admin);
        }

        // Crear usuario editor si no existe
        if (usuarioRepository.findByEmail("editor@example.com").isEmpty()) {
            Usuario editor = Usuario.builder()
                    .nombre("Editor")
                    .email("editor@example.com")
                    .contrasenha(passwordEncoder.encode("editor123"))
                    .rol(Rol.EDITOR)
                    .build();
            usuarioRepository.save(editor);
        }
    }
}