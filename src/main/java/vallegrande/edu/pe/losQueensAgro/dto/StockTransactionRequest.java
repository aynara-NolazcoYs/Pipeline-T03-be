package vallegrande.edu.pe.losQueensAgro.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockTransactionRequest {

    @NotNull(message = "El almacén es obligatorio")
    private Long warehouseId;

    @NotNull(message = "El producto es obligatorio")
    private Long productId;

    @NotNull(message = "La cantidad es obligatoria")
    private Integer quantity;
}
