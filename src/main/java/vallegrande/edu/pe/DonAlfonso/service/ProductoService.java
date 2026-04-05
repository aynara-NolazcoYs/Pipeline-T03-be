package vallegrande.edu.pe.DonAlfonso.service;

import vallegrande.edu.pe.DonAlfonso.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<Producto> findAll();

    List<Producto> findByEstado(String estado);

    Optional<Producto> findById(Long id);

    Producto save(Producto producto);

    Producto update(Producto producto);

    Producto delete(Long id);

    Producto restore(Long id);
}
