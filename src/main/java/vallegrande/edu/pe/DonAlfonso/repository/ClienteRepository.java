package vallegrande.edu.pe.DonAlfonso.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.edu.pe.DonAlfonso.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByEstadoIgnoreCase(String estado);
}
