package py.edu.facitec.ventas.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.facitec.ventas.entity.Vendedor;
import py.edu.facitec.ventas.enums.Horarios;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputRegistrarHorario {
    private String fechaHora;
    private String horarios;
    private Integer vendedorId;
}
