package vallegrande.edu.pe.DonAlfonso.rest;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vallegrande.edu.pe.DonAlfonso.model.Cliente;
import vallegrande.edu.pe.DonAlfonso.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteRest {

    private final ClienteService clienteService;

    public ClienteRest(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Lista todos los clientes (activos e inactivos).
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listar();
    }

    // Obtiene un cliente por identificador.
    @GetMapping("/{id}")
    public Cliente listarPorId(@PathVariable Integer id) {
        return clienteService.listarPorId(id);
    }

    // Filtra clientes por estado: A (activo) o I (inactivo).
    @GetMapping("/estado/{estado}")
    public List<Cliente> listarPorEstado(@PathVariable String estado) {
        return clienteService.listarPorEstado(estado);
    }

    // Crea un cliente nuevo y devuelve 201.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente crear(@Valid @RequestBody Cliente cliente) {
        return clienteService.crear(cliente);
    }

    // Actualiza los datos de un cliente existente.
    @PutMapping("/{id}")
    public Cliente editar(@PathVariable Integer id, @Valid @RequestBody Cliente cliente) {
        return clienteService.editar(id, cliente);
    }

    // Eliminacion logica: cambia estado a I y guarda deletedAt.
    @PatchMapping("/{id}/eliminar")
    public Cliente eliminarLogico(@PathVariable Integer id) {
        return clienteService.eliminarLogico(id);
    }

    // Restauracion logica: cambia estado a A y guarda restoredAt.
    @PatchMapping("/{id}/restaurar")
    public Cliente restaurarLogico(@PathVariable Integer id) {
        return clienteService.restaurarLogico(id);
    }
}
