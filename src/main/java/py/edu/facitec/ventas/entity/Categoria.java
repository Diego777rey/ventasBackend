package py.edu.facitec.ventas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity // esta entidad tendra una tabla en bd
@Table(name = "categorias")
@NoArgsConstructor
@AllArgsConstructor//esto se utliza para que no tengamos que construir los constructores ni los getters y setters
@Builder
public class Categoria {
    @Id    // incamos que el id es pk //autoincr es con genericgenerto
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false) //not null y unico
    private String descripcion;
    //normalmente aca no utilizamos las listas y hacemos un resolver para no complicarnos
}
