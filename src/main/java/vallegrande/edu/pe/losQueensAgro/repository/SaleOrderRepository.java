package vallegrande.edu.pe.losQueensAgro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.edu.pe.losQueensAgro.model.SaleOrder;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long> {
}