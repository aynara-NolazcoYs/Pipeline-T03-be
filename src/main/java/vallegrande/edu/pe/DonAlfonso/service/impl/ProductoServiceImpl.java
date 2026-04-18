package vallegrande.edu.pe.DonAlfonso.service.impl;

import org.springframework.stereotype.Service;
import vallegrande.edu.pe.DonAlfonso.model.Producto;
import vallegrande.edu.pe.DonAlfonso.repository.ProductoRepository;
import vallegrande.edu.pe.DonAlfonso.service.ProductoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> findByEstado(String estado) {
        return productoRepository.findByEstado(estado);
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto save(Producto producto) {
        producto.setId(null);
        producto.setUpdatedAt(null);
        producto.setDeletedAt(null);
        producto.setRestoredAt(null);
        return productoRepository.save(producto);
    }

    @Override
    public Producto update(Producto producto) {
        Producto existente = productoRepository.findById(producto.getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setCreatedAt(existente.getCreatedAt());
        producto.setUpdatedAt(LocalDateTime.now());
        producto.setEstado("A");

        return productoRepository.save(producto);
    }

    @Override
    public Producto delete(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstado("I");
        producto.setDeletedAt(LocalDateTime.now());

        return productoRepository.save(producto);
    }

    @Override
    public Producto restore(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstado("A");
        producto.setRestoredAt(LocalDateTime.now());

        return productoRepository.save(producto);
    }
}