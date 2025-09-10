package py.edu.facitec.ventas.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
