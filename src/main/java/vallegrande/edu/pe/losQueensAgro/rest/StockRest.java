package vallegrande.edu.pe.losQueensAgro.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vallegrande.edu.pe.losQueensAgro.dto.StockTransactionRequest;
import vallegrande.edu.pe.losQueensAgro.model.Stock;
import vallegrande.edu.pe.losQueensAgro.service.StockService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/stock")
@Tag(name = "Stock API", description = "API for stock management")
public class StockRest {

    private final StockService stockService;

    public StockRest(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    @Operation(summary = "Get All Stock", description = "Returns all stock from all warehouses")
    public List<Stock> findAll() {
        return stockService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Stock by ID", description = "Returns a specific stock by its ID")
    public Stock findById(@PathVariable Long id) {
        return stockService.findById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "Create or Update Stock", description = "Creates a new stock or updates existing stock for a product in a warehouse")
    public Stock createOrUpdateStock(@Valid @RequestBody StockTransactionRequest request) {
        return stockService.createOrUpdateStock(request);
    }
}
