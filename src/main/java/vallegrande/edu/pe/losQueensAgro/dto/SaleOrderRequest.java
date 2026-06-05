package vallegrande.edu.pe.losQueensAgro.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SaleOrderRequest {

    @NotNull(message = "La persona es obligatoria")
    private Long personId;

    @NotNull(message = "El almacén es obligatorio")
    private Long warehouseId;

    @NotBlank(message = "El tipo de entrega es obligatorio")
    @Size(max = 2, message = "El tipo de entrega debe tener como máximo 2 caracteres")
    private String deliveryType;

    @NotBlank(message = "El método de pago es obligatorio")
    @Size(max = 2, message = "El método de pago debe tener como máximo 2 caracteres")
    private String paymentMethod;

    @NotBlank(message = "Las notas son obligatorias")
    private String notes;

    @NotEmpty(message = "Debe agregar al menos un producto")
    private List<@Valid SaleItemRequest> items;
}