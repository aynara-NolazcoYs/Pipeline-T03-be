package vallegrande.edu.pe.losQueensAgro.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.edu.pe.losQueensAgro.dto.StockTransactionRequest;
import vallegrande.edu.pe.losQueensAgro.model.Stock;
import vallegrande.edu.pe.losQueensAgro.repository.StockRepository;
import vallegrande.edu.pe.losQueensAgro.service.StockService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));
    }

    @Override
    @Transactional
    public Stock createOrUpdateStock(StockTransactionRequest request) {
        
        // Buscar si existe stock para este producto en este almacén
        Optional<Stock> existingStock = stockRepository.findByProductIdAndWarehouseId(
            request.getProductId(), 
            request.getWarehouseId()
        );

        Stock stock;
        if (existingStock.isPresent()) {
            // Actualizar cantidad
            stock = existingStock.get();
            stock.setQuantity(stock.getQuantity() + request.getQuantity());
            stock.setLastUpdate(LocalDateTime.now());
        } else {
            // Crear nuevo
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
