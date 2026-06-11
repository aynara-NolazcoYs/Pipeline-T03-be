package vallegrande.edu.pe.losQueensAgro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vallegrande.edu.pe.losQueensAgro.model.Supplier;
import vallegrande.edu.pe.losQueensAgro.repository.SupplierRepository;
import vallegrande.edu.pe.losQueensAgro.service.SupplierService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository repository;

    @Override
    public List<Supplier> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Supplier> findByIsActive(Boolean isActive) {
        return repository.findByIsActive(isActive);
    }

    @Override
    public List<Supplier> findActive() {
        return repository.findAllNotDeleted();
    }

    @Override
    public Supplier findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Supplier create(Supplier supplier) {
        if (repository.existsByCommercialName(supplier.getCommercialName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un proveedor registrado con el Nombre Comercial ingresado");
        }
        if (repository.existsByRuc(supplier.getRuc())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un proveedor registrado con el RUC ingresado");
        }
        if (repository.existsByPhone(supplier.getPhone())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un proveedor registrado con el Teléfono ingresado");
        }
        if (repository.existsByEmail(supplier.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un proveedor registrado con el Correo ingresado");
        }

        supplier.setIsActive(true);
        supplier.setCreatedDate(LocalDateTime.now());
        return repository.save(supplier);
    }

    @Override
    public Supplier update(Integer id, Supplier supplier) {
        Supplier existing = findById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado");
        }

        if (repository.existsByCommercialNameAndIdNot(supplier.getCommercialName(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe otro proveedor registrado con el Nombre Comercial ingresado");
        }
        if (repository.existsByRucAndIdNot(supplier.getRuc(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe otro proveedor registrado con el RUC ingresado");
        }
        if (repository.existsByPhoneAndIdNot(supplier.getPhone(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe otro proveedor registrado con el Teléfono ingresado");
        }
        if (repository.existsByEmailAndIdNot(supplier.getEmail(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe otro proveedor registrado con el Correo ingresado");
        }

        existing.setCommercialName(supplier.getCommercialName());
        existing.setPhone(supplier.getPhone());
        existing.setEmail(supplier.getEmail());
        existing.setUbigeoCode(supplier.getUbigeoCode());
        existing.setRuc(supplier.getRuc());
        existing.setAddress(supplier.getAddress());
        existing.setUpdateDate(LocalDateTime.now());
        return repository.save(existing);
    }

    @Override
    public Supplier delete(Integer id) {
        Supplier existing = findById(id);
        if (existing != null) {
            existing.setIsActive(false);  // Inactivo
            existing.setDeletedDate(LocalDateTime.now());
            return repository.save(existing);
        }
        return null;
    }

    @Override
    public Supplier restore(Integer id) {
        Supplier existing = findById(id);
        if (existing != null) {
            existing.setIsActive(true);  // Restaurar a activo
            existing.setRestoredDate(LocalDateTime.now());
            return repository.save(existing);
        }
        return null;
    }
}