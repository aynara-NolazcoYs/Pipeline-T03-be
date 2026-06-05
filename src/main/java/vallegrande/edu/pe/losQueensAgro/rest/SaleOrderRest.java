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
import vallegrande.edu.pe.losQueensAgro.dto.SaleOrderRequest;
import vallegrande.edu.pe.losQueensAgro.model.SaleOrder;
import vallegrande.edu.pe.losQueensAgro.service.SaleOrderService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sales API", description = "API for sale transactions")
public class SaleOrderRest {

    private final SaleOrderService saleOrderService;

    public SaleOrderRest(SaleOrderService saleOrderService) {
        this.saleOrderService = saleOrderService;
    }

    @GetMapping
    @Operation(summary = "Get All Sales", description = "Returns all sale transactions")
    public List<SaleOrder> findAll() {
        return saleOrderService.findAll();
    }

    @PostMapping("/save")
    @Operation(summary = "Register Sale", description = "Registers a sale transaction and decreases product quantity")
    public SaleOrder registerSale(@Valid @RequestBody SaleOrderRequest request) {
        return saleOrderService.registerSale(request);
    }
}