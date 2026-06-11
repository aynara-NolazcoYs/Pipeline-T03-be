package vallegrande.edu.pe.losQueensAgro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.edu.pe.losQueensAgro.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
