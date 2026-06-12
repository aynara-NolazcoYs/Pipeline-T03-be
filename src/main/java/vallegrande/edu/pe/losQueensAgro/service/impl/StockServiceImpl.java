package vallegrande.edu.pe.losQueensAgro.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vallegrande.edu.pe.losQueensAgro.dto.StockTransactionRequest;
import vallegrande.edu.pe.losQueensAgro.model.Stock;
import vallegrande.edu.pe.losQueensAgro.model.product;
import vallegrande.edu.pe.losQueensAgro.repository.StockRepository;
import vallegrande.edu.pe.losQueensAgro.service.ProductService;
import vallegrande.edu.pe.losQueensAgro.service.StockService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ProductService productService;

    public StockServiceImpl(StockRepository stockRepository, ProductService productService) {
        this.stockRepository = stockRepository;
        this.productService = productService;
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Stock no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public Stock createOrUpdateStock(StockTransactionRequest request) {

        // Validar que la cantidad sea mayor a cero
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La cantidad debe ser mayor a cero");
        }

        // Validar que el producto existe
        product prod = productService.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El producto con ID " + request.getProductId() + " no existe"));

        // Validar que el producto esté activo
        if (!"A".equalsIgnoreCase(prod.getState())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El producto '" + prod.getName() + "' no está activo y no puede operar en stock");
        }

        // Buscar si ya existe stock para este producto en este almacén
        Optional<Stock> existingStock = stockRepository.findByProductIdAndWarehouseId(
                request.getProductId(),
                request.getWarehouseId()
        );

        Stock stock;
        if (existingStock.isPresent()) {
            stock = existingStock.get();

            // Validar que el stock resultante no sea negativo
            int nuevoTotal = stock.getQuantity() + request.getQuantity();
            if (nuevoTotal < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "La operación dejaría el stock en negativo. Stock actual: "
                        + stock.getQuantity() + ", Cambio solicitado: " + request.getQuantity());
            }

            stock.setQuantity(nuevoTotal);
            stock.setLastUpdate(LocalDateTime.now());
        } else {
            // Crear nuevo registro de stock
            stock = Stock.builder()
                    .productId(request.getProductId())
                    .warehouseId(request.getWarehouseId())
                    .quantity(request.getQuantity())
                    .lastUpdate(LocalDateTime.now())
                    .status("A")
                    .build();
        }

        return stockRepository.save(stock);
    }
}
