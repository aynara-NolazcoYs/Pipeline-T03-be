package vallegrande.edu.pe.DonAlfonso.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.edu.pe.DonAlfonso.model.Producto;
import vallegrande.edu.pe.DonAlfonso.service.ProductoService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/producto")
@Tag(name = "Producto API", description = "API for Producto management")
public class ProductoRest {

    private final ProductoService productoService;

    @Autowired
    public ProductoRest(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(summary = "Get All Producto", description = "Get All Producto")
    public List<Producto> findAll(){
        return productoService.findAll();
    }

    @GetMapping("/state/{estado}")
    @Operation(summary = "Get Producto By STATE", description = "Get Producto By STATE")
    public List<Producto> findByEstado(@PathVariable String estado) {
        return productoService.findByEstado(estado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Producto By ID", description = "Get Producto By ID")
    public Optional<Producto> findById(@PathVariable Long id) {
        return productoService.findById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "Save Producto", description = "Save Producto")
    public Producto save(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update Producto", description = "Update Producto")
    public Producto update(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        return productoService.update(producto);
    }

    @PatchMapping("/delete/{id}")
    @Operation(summary = "Logical Delete Producto", description = "Logical Delete Producto")
    public Producto delete(@PathVariable Long id) {
        return productoService.delete(id);
    }

    @PatchMapping("/restore/{id}")
    @Operation(summary = "Logical Restore Producto", description = "Logical Restore Producto")
    public Producto restore(@PathVariable Long id) {
        return productoService.restore(id);
    }
}
