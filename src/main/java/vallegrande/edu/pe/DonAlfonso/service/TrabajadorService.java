package vallegrande.edu.pe.DonAlfonso.service;

import vallegrande.edu.pe.DonAlfonso.model.Trabajador;
import vallegrande.edu.pe.DonAlfonso.repository.TrabajadorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrabajadorService {

    private final TrabajadorRepository repository;

    public TrabajadorService(TrabajadorRepository repository) {
        this.repository = repository;
    }

    public List<Trabajador> listar() {
        return repository.findAll();
    }

    public Trabajador listarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Trabajador> listarPorEstado(String estado) {
        return repository.findByEstado(estado);
    }

    // Registrar: limpia IDs previos y deja auditoria de edicion/eliminacion/restauracion en null.
    public Trabajador crear(Trabajador t) {
        t.setIdentificador(null);
        t.setUpdatedAt(null);
        t.setDeletedAt(null);
        t.setRestoredAt(null);
        if (t.getEstado() == null || t.getEstado().isBlank()) {
            t.setEstado("A");
        }
        return repository.save(t);
    }

    public Trabajador editar(Integer id, Trabajador t) {
        Trabajador existente = listarPorId(id);
        if (existente == null) return null;

        existente.setNombre(t.getNombre());
        existente.setCelular(t.getCelular());
        existente.setCargo(t.getCargo());
        existente.setNumeroDocumento(t.getNumeroDocumento());
        existente.setFechaIngreso(t.getFechaIngreso());
        // Editar: registra fecha y hora de actualizacion.
        existente.setUpdatedAt(LocalDateTime.now());

        return repository.save(existente);
    }

    public Trabajador eliminar(Integer id) {
        Trabajador t = listarPorId(id);
        if (t != null) {
            t.setEstado("I");
            // Eliminar logico: registra fecha y hora de eliminacion.
            t.setDeletedAt(LocalDateTime.now());
            return repository.save(t);
        }
        return null;
    }

    public Trabajador restaurar(Integer id) {
        Trabajador t = listarPorId(id);
        if (t != null) {
            t.setEstado("A");
            // Restaurar logico: registra fecha y hora de restauracion.
            t.setRestoredAt(LocalDateTime.now());
            return repository.save(t);
        }
        return null;
    }
}
