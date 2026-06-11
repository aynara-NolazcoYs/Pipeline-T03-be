package vallegrande.edu.pe.losQueensAgro.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vallegrande.edu.pe.losQueensAgro.model.StockMovement;
import vallegrande.edu.pe.losQueensAgro.repository.StockMovementRepository;
import vallegrande.edu.pe.losQueensAgro.service.PersonService;
import vallegrande.edu.pe.losQueensAgro.service.ProductService;
import vallegrande.edu.pe.losQueensAgro.service.StockMovementService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductService productService;
    private final PersonService personService;

    public StockMovementServiceImpl(StockMovementRepository stockMovementRepository,
                                    ProductService productService,
                                    PersonService personService) {
        this.stockMovementRepository = stockMovementRepository;
        this.productService = productService;
        this.personService = personService;
    }

    @Override
    public List<StockMovement> findAll() {
        return stockMovementRepository.findAll();
    }

    @Override
    @Transactional
    public StockMovement registerMovement(StockMovement movement) {
        if (movement.getMovementType() == null || movement.getQuantity() == null || movement.getProductId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de movimiento, cantidad y producto son obligatorios");
        }

        // Validar que el producto exista
        if (!productService.findById(movement.getProductId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto con ID " + movement.getProductId() + " no existe");
        }

        // Validar que la persona exista
        if (movement.getPersonId() != null && !personService.findById(movement.getPersonId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La persona con ID " + movement.getPersonId() + " no existe");
        }

        // Registrar entrada ('E') o salida ('S') y actualizar el stock del producto
        if ("E".equalsIgnoreCase(movement.getMovementType())) {
            productService.increaseStock(movement.getProductId(), movement.getQuantity());
        } else if ("S".equalsIgnoreCase(movement.getMovementType())) {
            productService.decreaseStock(movement.getProductId(), movement.getQuantity());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de movimiento inválido (debe ser 'E' o 'S')");
        }

        // Configurar la fecha/hora en zona Lima
        movement.setMovementDate(LocalDateTime.now(ZoneId.of("America/Lima")));

        return stockMovementRepository.save(movement);
    }
}
