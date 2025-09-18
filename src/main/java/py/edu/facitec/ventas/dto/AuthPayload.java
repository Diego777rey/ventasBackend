package py.edu.facitec.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import py.edu.facitec.ventas.entity.Usuario;

@Data
@AllArgsConstructor
public class AuthPayload {
    private Usuario usuario;   // Puede ser null si falla
    private String token;   // Puede ser null si falla
    private String error;   // Mensaje de error opcional
}
