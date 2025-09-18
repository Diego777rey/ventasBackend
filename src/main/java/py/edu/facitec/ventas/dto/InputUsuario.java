package py.edu.facitec.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.facitec.ventas.enums.Rol;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputUsuario {
    private String nombre;
    private String contrasenha;
    private String email;
    private Rol rol;
}
