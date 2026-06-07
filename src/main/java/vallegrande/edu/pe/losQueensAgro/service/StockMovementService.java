package vallegrande.edu.pe.losQueensAgro.service;

import vallegrande.edu.pe.losQueensAgro.model.StockMovement;
import java.util.List;

public interface StockMovementService {
    List<StockMovement> findAll();
    StockMovement registerMovement(StockMovement movement);
}
