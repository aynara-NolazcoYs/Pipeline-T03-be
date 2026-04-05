package vallegrande.edu.pe.DonAlfonso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.edu.pe.DonAlfonso.model.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByEstado(String estado);
}
