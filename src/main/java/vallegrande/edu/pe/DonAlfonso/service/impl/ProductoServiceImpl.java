package vallegrande.edu.pe.DonAlfonso.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vallegrande.edu.pe.DonAlfonso.model.Producto;
import vallegrande.edu.pe.DonAlfonso.repository.ProductoRepository;
import vallegrande.edu.pe.DonAlfonso.service.ProductoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> findAll() {
        log.info("Listando Datos");
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> findByEstado(String estado) {
        log.info("Listando Datos por Estado: " + estado);
        return productoRepository.findByEstado(estado);
    }

    @Override
    public Optional<Producto> findById(Long id) {
        log.info("Listando Datos por ID: " + id);
        return productoRepository.findById(id);
    }

    @Override
    public Producto save(Producto producto) {
        log.info("Registrando Datos: " + producto);
        // Limpiar ID para forzar INSERT (no merge)
        producto.setId(null);
        // Limpiar timestamps antiguos
        producto.setUpdatedAt(null);
        producto.setDeletedAt(null);
        producto.setRestoredAt(null);
        // Establecer valores por defecto
        producto.setEstado("A");
        producto.setCreatedAt(LocalDateTime.now());
        return productoRepository.save(producto);
    }

    @Override
    public Producto update(Producto producto) {
        log.info("Editando Datos: " + producto);

        Producto existente = productoRepository.findById(producto.getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + producto.getId()));

        producto.setCreatedAt(existente.getCreatedAt());
        producto.setEstado("A");
        producto.setUpdatedAt(LocalDateTime.now());

        return productoRepository.save(producto);
    }

    @Override
    public Producto delete(Long id) {
        log.info("Eliminando Datos: " + id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstado("I");
        producto.setDeletedAt(LocalDateTime.now());

        return productoRepository.save(producto);
    }

    @Override
    public Producto restore(Long id) {
        log.info("Restaurando Datos: " + id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstado("A");
        producto.setRestoredAt(LocalDateTime.now());

        return productoRepository.save(producto);
    }
}
