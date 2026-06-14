package vallegrande.edu.pe.losQueensAgro.service;

import vallegrande.edu.pe.losQueensAgro.dto.PersonRequest;
import vallegrande.edu.pe.losQueensAgro.dto.LoginRequest;
import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<PersonRequest> findAll();
    List<PersonRequest> findByState(String state);
    Optional<PersonRequest> findById(Long id);
    PersonRequest save(PersonRequest personRequest);
    PersonRequest update(Long id, PersonRequest personRequest);
    PersonRequest delete(Long id);
    PersonRequest restore(Long id);
    PersonRequest login(LoginRequest loginRequest);
}
