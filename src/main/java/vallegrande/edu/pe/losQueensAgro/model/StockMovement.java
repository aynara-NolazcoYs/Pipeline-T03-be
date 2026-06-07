package vallegrande.edu.pe.losQueensAgro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "movement_type")
    private String movementType;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "reason")
    private String reason;

    @Column(name = "movement_date")
    private LocalDateTime movementDate;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "order_Details_id")
    private Long orderDetailsId;
}
