package py.edu.facitec.ventas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
@Data
@Entity
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor//esto se utliza para que no tengamos que construir los constructores ni los getters y setters
@SuperBuilder
public class Cliente extends Persona{
    @Column(nullable = false)
    private String fechaRegistro;
}