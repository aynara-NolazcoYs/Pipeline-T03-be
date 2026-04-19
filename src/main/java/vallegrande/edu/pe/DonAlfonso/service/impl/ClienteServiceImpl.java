package vallegrande.edu.pe.DonAlfonso.service.impl;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vallegrande.edu.pe.DonAlfonso.model.Cliente;
import vallegrande.edu.pe.DonAlfonso.repository.ClienteRepository;
import vallegrande.edu.pe.DonAlfonso.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    // Retorna todos los clientes registrados.
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    // Retorna un cliente por ID o lanza 404 si no existe.
    public Cliente listarPorId(Integer id) {
        return buscarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    // Filtra por estado logico del cliente.
    public List<Cliente> listarPorEstado(String estado) {
        return clienteRepository.findByEstadoIgnoreCase(estado);
    }

    @Override
    @Transactional
    // Registro: estado por defecto A y campos de auditoria iniciales.
    public Cliente crear(Cliente cliente) {
        if (cliente.getEstado() == null || cliente.getEstado().isBlank()) {
            cliente.setEstado("A");
        }
        cliente.setIdentificador(null);
        cliente.setUpdatedAt(null);
        cliente.setDeletedAt(null);
        cliente.setRestoredAt(null);
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    // Edicion: actualiza datos de negocio y marca fecha/hora de actualizacion.
    public Cliente editar(Integer id, Cliente clienteActualizado) {
        Cliente cliente = buscarPorId(id);

        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setCelular(clienteActualizado.getCelular());
        cliente.setCorreo(clienteActualizado.getCorreo());
        cliente.setRuc(clienteActualizado.getRuc());
        cliente.setDireccion(clienteActualizado.getDireccion());

        if (clienteActualizado.getEstado() != null && !clienteActualizado.getEstado().isBlank()) {
            cliente.setEstado(clienteActualizado.getEstado().toUpperCase());
        }

        cliente.setUpdatedAt(LocalDateTime.now());

        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    // Eliminacion logica: mantiene registro, cambia estado a I y marca deletedAt.
    public Cliente eliminarLogico(Integer id) {
        Cliente cliente = buscarPorId(id);
        cliente.setEstado("I");
        cliente.setDeletedAt(LocalDateTime.now());
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    // Restauracion logica: reactiva cliente y marca restoredAt.
    public Cliente restaurarLogico(Integer id) {
        Cliente cliente = buscarPorId(id);
        cliente.setEstado("A");
        cliente.setRestoredAt(LocalDateTime.now());
        return clienteRepository.save(cliente);
    }

    // Metodo interno reutilizable para centralizar la validacion de existencia.
    private Cliente buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente no encontrado con ID: " + id));
    }
}
