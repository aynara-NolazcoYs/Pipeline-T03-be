package vallegrande.edu.pe.losQueensAgro.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vallegrande.edu.pe.losQueensAgro.model.StockMovement;
import vallegrande.edu.pe.losQueensAgro.service.StockMovementService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/stock-movement")
@Tag(name = "Stock Movement API", description = "API for stock movement (kárdex)")
public class StockMovementRest {

    private final StockMovementService stockMovementService;

    public StockMovementRest(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @GetMapping
    @Operation(summary = "Get All Movements", description = "Returns all stock movements")
    public List<StockMovement> findAll() {
        return stockMovementService.findAll();
    }

    @PostMapping("/save")
    @Operation(summary = "Register Movement", description = "Registers a new stock movement and updates product quantity")
    public StockMovement registerMovement(@Valid @RequestBody StockMovement movement) {
        return stockMovementService.registerMovement(movement);
    }
}
