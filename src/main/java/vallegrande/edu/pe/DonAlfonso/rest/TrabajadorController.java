package vallegrande.edu.pe.DonAlfonso.rest;

import vallegrande.edu.pe.DonAlfonso.model.Trabajador;
import vallegrande.edu.pe.DonAlfonso.service.TrabajadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trabajador")
@CrossOrigin("*")
public class TrabajadorController {

    private final TrabajadorService service;

    public TrabajadorController(TrabajadorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Trabajador> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Trabajador listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    @GetMapping("/estado/{estado}")
    public List<Trabajador> listarPorEstado(@PathVariable String estado) {
        return service.listarPorEstado(estado);
    }

    @PostMapping
    public Trabajador crear(@RequestBody Trabajador t) {
        return service.crear(t);
    }

    @PutMapping("/{id}")
    public Trabajador editar(@PathVariable Integer id, @RequestBody Trabajador t) {
        return service.editar(id, t);
    }

    @PatchMapping("/eliminar/{id}")
    public Trabajador eliminar(@PathVariable Integer id) {
        return service.eliminar(id);
    }

    @PatchMapping("/restaurar/{id}")
    public Trabajador restaurar(@PathVariable Integer id) {
        return service.restaurar(id);
    }
}
