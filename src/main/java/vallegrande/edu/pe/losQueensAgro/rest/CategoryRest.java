package vallegrande.edu.pe.losQueensAgro.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vallegrande.edu.pe.losQueensAgro.model.Category;
import vallegrande.edu.pe.losQueensAgro.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryRest {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }
    
}
