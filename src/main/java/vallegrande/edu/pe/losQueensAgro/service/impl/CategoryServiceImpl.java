package vallegrande.edu.pe.losQueensAgro.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vallegrande.edu.pe.losQueensAgro.model.Category;
import vallegrande.edu.pe.losQueensAgro.repository.CategoryRepository;
import vallegrande.edu.pe.losQueensAgro.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
}
