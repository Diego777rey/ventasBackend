package py.edu.facitec.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDto {
    private Date fechaRegistro;
}
//los dtos se utilizan como medio de seguridad para no exponer datos sensibles