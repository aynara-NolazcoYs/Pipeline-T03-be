package vallegrande.edu.pe.losQueensAgro.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vallegrande.edu.pe.losQueensAgro.dto.PersonRequest;
import vallegrande.edu.pe.losQueensAgro.model.Person;
import vallegrande.edu.pe.losQueensAgro.repository.PersonRepository;
import vallegrande.edu.pe.losQueensAgro.service.PersonService;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Obtener todas las personas
    @Override
    public List<PersonRequest> findAll() {
        return personRepository.findAll()
                .stream()
                .map(this::convertToRequest)
                .collect(Collectors.toList());
    }

    // Buscar por estado (A = activo, I = inactivo)
    @Override
    public List<PersonRequest> findByState(String state) {
        return personRepository.findByState(state)
                .stream()
                .map(this::convertToRequest)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    @Override
    public Optional<PersonRequest> findById(Long id) {
        return personRepository.findById(id)
                .map(this::convertToRequest);
    }

    // Guardar nueva persona
    @Override
    public PersonRequest save(PersonRequest personRequest) {
        // Validar que el email no exista
        if (personRepository.findByEmail(personRequest.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }

        // Validar que el documento no exista
        if (personRepository.findByDocumentNumber(personRequest.getDocument_number()).isPresent()) {
            throw new RuntimeException("El número de documento ya está registrado");
        }

        Person person = new Person();
        person.setUbigeo_code(personRequest.getUbigeo_code());
        person.setName(personRequest.getName());
        person.setLast_name(personRequest.getLast_name());
        person.setDocument_type(personRequest.getDocument_type());
        person.setDocument_number(personRequest.getDocument_number());
        person.setPhone(personRequest.getPhone());
        person.setEmail(personRequest.getEmail());
        person.setRole(personRequest.getRole());
        person.setStreet(personRequest.getStreet());
        // Hashear la contraseña antes de guardar
        person.setPassword(passwordEncoder.encode(personRequest.getPassword()));
        person.setState(personRequest.getState() != null ? personRequest.getState() : "A");
        person.setCreated_date(LocalDateTime.now());

        Person saved = personRepository.save(person);
        return convertToRequest(saved);
    }

    // Actualizar persona
    @Override
    public PersonRequest update(Long id, PersonRequest personRequest) {
        Person existente = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));

        // Validar email único (si cambió)
        if (!existente.getEmail().equals(personRequest.getEmail()) &&
            personRepository.findByEmail(personRequest.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }

        // Validar documento único (si cambió)
        if (!existente.getDocument_number().equals(personRequest.getDocument_number()) &&
            personRepository.findByDocumentNumber(personRequest.getDocument_number()).isPresent()) {
            throw new RuntimeException("El número de documento ya está registrado");
        }

        existente.setUbigeo_code(personRequest.getUbigeo_code());
        existente.setName(personRequest.getName());
        existente.setLast_name(personRequest.getLast_name());
        existente.setDocument_type(personRequest.getDocument_type());
        existente.setDocument_number(personRequest.getDocument_number());
        existente.setPhone(personRequest.getPhone());
        existente.setEmail(personRequest.getEmail());
        existente.setRole(personRequest.getRole());
        existente.setStreet(personRequest.getStreet());
        // Hashear la nueva contraseña solo si fue enviada
        if (personRequest.getPassword() != null && !personRequest.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(personRequest.getPassword()));
        }
        existente.setState(personRequest.getState());
        existente.setUpdate_date(LocalDateTime.now());

        Person updated = personRepository.save(existente);
        return convertToRequest(updated);
    }

    // Eliminación lógica
    @Override
    public PersonRequest delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));

        person.setState("I");
        person.setDeleted_date(LocalDateTime.now());

        Person deleted = personRepository.save(person);
        return convertToRequest(deleted);
    }

    // Restaurar persona
    @Override
    public PersonRequest restore(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));

        person.setState("A");
        person.setRestored_date(LocalDateTime.now());

        Person restored = personRepository.save(person);
        return convertToRequest(restored);
    }

   
    // La contraseña NO se incluye en la respuesta por seguridad
    private PersonRequest convertToRequest(Person person) {
        return PersonRequest.builder()
                .ubigeo_code(person.getUbigeo_code())
                .name(person.getName())
                .last_name(person.getLast_name())
                .document_type(person.getDocument_type())
                .document_number(person.getDocument_number())
                .phone(person.getPhone())
                .email(person.getEmail())
                .role(person.getRole())
                .street(person.getStreet())
                .password(null) // No exponer la contraseña hasheada en las respuestas
                .state(person.getState())
                .build();
    }
}