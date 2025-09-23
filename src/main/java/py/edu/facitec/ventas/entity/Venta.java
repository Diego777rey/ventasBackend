package py.edu.facitec.ventas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.facitec.ventas.enums.TipoPago;

@Data
@Entity
@Table(name = "ventas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fecha;
    private TipoPago tipoPago;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Vendedor vendedor;

}
