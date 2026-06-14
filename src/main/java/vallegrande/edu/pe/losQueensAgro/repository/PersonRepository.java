package vallegrande.edu.pe.losQueensAgro.repository;

import vallegrande.edu.pe.losQueensAgro.model.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByState(String state);
    Optional<Person> findByEmail(String email);
    
    @Query("SELECT p FROM Person p WHERE p.document_number = :documentNumber")
    Optional<Person> findByDocumentNumber(@Param("documentNumber") String documentNumber);
}