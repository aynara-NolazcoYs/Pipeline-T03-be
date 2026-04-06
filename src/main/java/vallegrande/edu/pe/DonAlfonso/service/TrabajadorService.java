package vallegrande.edu.pe.DonAlfonso.service;

import vallegrande.edu.pe.DonAlfonso.model.Trabajador;
import vallegrande.edu.pe.DonAlfonso.repository.TrabajadorRepository;
import org.springframework.stereotype.Service;

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

    public Trabajador crear(Trabajador t) {
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

        return repository.save(existente);
    }

    public Trabajador eliminar(Integer id) {
        Trabajador t = listarPorId(id);
        if (t != null) {
            t.setEstado("I");
            return repository.save(t);
        }
        return null;
    }

    public Trabajador restaurar(Integer id) {
        Trabajador t = listarPorId(id);
        if (t != null) {
            t.setEstado("A");
            return repository.save(t);
        }
        return null;
    }
}
