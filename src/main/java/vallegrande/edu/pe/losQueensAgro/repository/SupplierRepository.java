package vallegrande.edu.pe.losQueensAgro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vallegrande.edu.pe.losQueensAgro.model.Supplier;
import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    List<Supplier> findByIsActive(Boolean isActive);
    
    @Query("SELECT s FROM Supplier s WHERE s.deletedDate IS NULL")
    List<Supplier> findAllNotDeleted();

    boolean existsByRuc(String ruc);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByCommercialName(String commercialName);

    boolean existsByRucAndIdNot(String ruc, Integer id);
    boolean existsByPhoneAndIdNot(String phone, Integer id);
    boolean existsByEmailAndIdNot(String email, Integer id);
    boolean existsByCommercialNameAndIdNot(String commercialName, Integer id);
}