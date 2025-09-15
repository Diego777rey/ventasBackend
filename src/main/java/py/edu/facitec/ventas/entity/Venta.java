package py.edu.facitec.ventas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ventas")
@NoArgsConstructor
@AllArgsConstructor//esto se utliza para que no tengamos que construir los constructores ni los getters y setters
@Builder
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fecha;
    private String tipoPago;

    @ManyToOne
    @JoinColumn(nullable = false)//obligatorio
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Vendedor vendedor;
}
