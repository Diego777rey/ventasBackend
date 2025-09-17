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
@AllArgsConstructor
@Builder
public class VentaDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cantidad;
    private Float precio;
    private Float subtotal;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Producto producto;
}
