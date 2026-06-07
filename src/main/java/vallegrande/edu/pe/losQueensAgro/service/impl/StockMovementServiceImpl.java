package vallegrande.edu.pe.losQueensAgro.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.edu.pe.losQueensAgro.model.StockMovement;
import vallegrande.edu.pe.losQueensAgro.repository.StockMovementRepository;
import vallegrande.edu.pe.losQueensAgro.service.ProductService;
import vallegrande.edu.pe.losQueensAgro.service.StockMovementService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductService productService;

    public StockMovementServiceImpl(StockMovementRepository stockMovementRepository,
                                    ProductService productService) {
        this.stockMovementRepository = stockMovementRepository;
        this.productService = productService;
    }

    @Override
    public List<StockMovement> findAll() {
        return stockMovementRepository.findAll();
    }

    @Override
    @Transactional
    public StockMovement registerMovement(StockMovement movement) {
        if (movement.getMovementType() == null || movement.getQuantity() == null || movement.getProductId() == null) {
            throw new IllegalArgumentException("El tipo de movimiento, cantidad y producto son obligatorios");
        }

        // Registrar entrada ('E') o salida ('S') y actualizar el stock del producto
        if ("E".equalsIgnoreCase(movement.getMovementType())) {
            productService.increaseStock(movement.getProductId(), movement.getQuantity());
        } else if ("S".equalsIgnoreCase(movement.getMovementType())) {
            productService.decreaseStock(movement.getProductId(), movement.getQuantity());
        } else {
            throw new IllegalArgumentException("Tipo de movimiento inválido (debe ser 'E' o 'S')");
        }

        // Configurar la fecha/hora en zona Lima
        movement.setMovementDate(LocalDateTime.now(ZoneId.of("America/Lima")));

        return stockMovementRepository.save(movement);
    }
}
