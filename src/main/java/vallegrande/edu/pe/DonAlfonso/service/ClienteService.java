package vallegrande.edu.pe.DonAlfonso.service;

import java.util.List;
import vallegrande.edu.pe.DonAlfonso.model.Cliente;

// Contrato de negocio para operaciones CRUD y auditoria de Cliente.
public interface ClienteService {
    // Consulta general.
    List<Cliente> listar();

    // Consulta por ID.
    Cliente listarPorId(Integer id);

    // Consulta por estado logico.
    List<Cliente> listarPorEstado(String estado);

    // Registro de cliente.
    Cliente crear(Cliente cliente);

    // Edicion de cliente.
    Cliente editar(Integer id, Cliente cliente);

    // Eliminacion logica (estado I).
    Cliente eliminarLogico(Integer id);

    // Restauracion logica (estado A).
    Cliente restaurarLogico(Integer id);
}
