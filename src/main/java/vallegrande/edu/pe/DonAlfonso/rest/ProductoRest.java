package vallegrande.edu.pe.DonAlfonso.rest;

import org.springframework.web.bind.annotation.*;
import vallegrande.edu.pe.DonAlfonso.model.Producto;
import vallegrande.edu.pe.DonAlfonso.service.ProductoService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/producto")
public class ProductoRest {

    private final ProductoService productoService;

    public ProductoRest(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> findAll() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Producto> findById(@PathVariable Long id) {
        return productoService.findById(id);
    }

    @GetMapping("/state/{estado}")
    public List<Producto> findByEstado(@PathVariable String estado) {
        return productoService.findByEstado(estado);
    }

    @PostMapping("/save")
    public Producto save(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/update/{id}")
    public Producto update(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        return productoService.update(producto);
    }

    @PatchMapping("/delete/{id}")
    public Producto delete(@PathVariable Long id) {
        return productoService.delete(id);
    }

    @PatchMapping("/restore/{id}")
    public Producto restore(@PathVariable Long id) {
        return productoService.restore(id);
    }
}