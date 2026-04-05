package vallegrande.edu.pe.DonAlfonso.service;

import java.util.List;
import vallegrande.edu.pe.DonAlfonso.model.Cliente;

public interface ClienteService {
    List<Cliente> listar();
    Cliente listarPorId(Integer id);
    List<Cliente> listarPorEstado(String estado);
    Cliente crear(Cliente cliente);
    Cliente editar(Integer id, Cliente cliente);
    Cliente eliminarLogico(Integer id);
    Cliente restaurarLogico(Integer id);
}
