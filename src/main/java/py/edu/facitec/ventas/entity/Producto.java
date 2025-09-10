package py.edu.facitec.ventas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "productos")
@NoArgsConstructor
@AllArgsConstructor//esto se utliza para que no tengamos que construir los constructores ni los getters y setters
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//autoincrement
    private int id;
    @Column(nullable = false, unique = true)
    private String descripcion;
    private Double precioCompra;
    private Double precioVenta;
    private Integer stock;
    private Boolean activo;
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)//la columna de union de ellos clave for
    private Categoria categoria;
}
