package vallegrande.edu.pe.DonAlfonso.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.edu.pe.DonAlfonso.model.Cliente;

// Acceso a datos de Cliente con consultas generadas por Spring Data JPA.
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Busca clientes por estado ignorando mayusculas/minusculas.
    List<Cliente> findByEstadoIgnoreCase(String estado);
}
