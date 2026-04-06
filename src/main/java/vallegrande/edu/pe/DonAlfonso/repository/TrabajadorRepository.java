package vallegrande.edu.pe.DonAlfonso.repository;

import vallegrande.edu.pe.DonAlfonso.model.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {

    List<Trabajador> findByEstado(String estado);
}