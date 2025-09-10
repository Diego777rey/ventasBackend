package py.edu.facitec.ventas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "venta_detalles")
@NoArgsConstructor
@AllArgsConstructor//esto se utliza para que no tengamos que construir los constructores ni los getters y setters
@Builder
public class VentaDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int cantidad;
    @Column(nullable = false)
    private Double precio;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Producto producto;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Venta venta;

}
