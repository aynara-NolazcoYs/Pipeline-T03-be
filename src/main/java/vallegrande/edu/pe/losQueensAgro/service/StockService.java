package vallegrande.edu.pe.losQueensAgro.service;

import vallegrande.edu.pe.losQueensAgro.dto.StockTransactionRequest;
import vallegrande.edu.pe.losQueensAgro.model.Stock;

import java.util.List;

public interface StockService {

    List<Stock> findAll();

    Stock findById(Long id);

    Stock createOrUpdateStock(StockTransactionRequest request);
}
