package vallegrande.edu.pe.losQueensAgro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.edu.pe.losQueensAgro.model.Supplier;
import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    List<Supplier> findByStatus(String status);
}