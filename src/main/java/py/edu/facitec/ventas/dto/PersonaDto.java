package py.edu.facitec.ventas.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.facitec.ventas.enums.RolPersona;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaDto {
    private int id;
    private String nombre;
    private String apellido;
    private String documento;
    private String telefono;
    private String email;
    private Boolean activo;
    private Set<RolPersona> roles;
}
