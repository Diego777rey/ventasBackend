package py.edu.facitec.ventas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.facitec.ventas.enums.Rol;

@Data
@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor//esto se utliza para que no tengamos que construir los constructores ni los getters y setters
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String contrasenha;
    @Column(nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;
}