package vallegrande.edu.pe.losQueensAgro.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vallegrande.edu.pe.losQueensAgro.dto.PersonRequest;
import vallegrande.edu.pe.losQueensAgro.dto.LoginRequest;
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
        String email = safeTrim(personRequest.getEmail());
        String docNum = safeTrim(personRequest.getDocument_number());

        // Validar que el email no exista
        if (email != null && personRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }

        // Validar que el documento no exista
        if (docNum != null && personRepository.findByDocumentNumber(docNum).isPresent()) {
            throw new RuntimeException("El número de documento ya está registrado");
        }

        Person person = new Person();
        person.setUbigeo_code(safeTrim(personRequest.getUbigeo_code()));
        person.setName(safeTrim(personRequest.getName()));
        person.setLast_name(safeTrim(personRequest.getLast_name()));
        person.setDocument_type(safeTrim(personRequest.getDocument_type()));
        person.setDocument_number(docNum);
        person.setPhone(safeTrim(personRequest.getPhone()));
        person.setEmail(email);
        person.setRole(safeTrim(personRequest.getRole()));
        person.setStreet(safeTrim(personRequest.getStreet()));
        // Validar que la contraseña esté presente al crear
        if (personRequest.getPassword() == null || personRequest.getPassword().isBlank()) {
            throw new RuntimeException("La contraseña es obligatoria al registrar una persona");
        }
        // Hashear la contraseña antes de guardar
        person.setPassword(passwordEncoder.encode(personRequest.getPassword()));
        person.setState(personRequest.getState() != null ? safeTrim(personRequest.getState()) : "A");
        person.setCreated_date(LocalDateTime.now());

        Person saved = personRepository.save(person);
        return convertToRequest(saved);
    }

    // Actualizar persona
    @Override
    public PersonRequest update(Long id, PersonRequest personRequest) {
        Person existente = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));

        // Validar email único (ignorar el de la misma persona)
        String requestEmail = safeTrim(personRequest.getEmail());
        if (requestEmail != null) {
            Optional<Person> personWithEmail = personRepository.findByEmail(requestEmail);
            if (personWithEmail.isPresent() && !personWithEmail.get().getId().equals(id)) {
                throw new RuntimeException("El correo electrónico ya está registrado");
            }
        }

        // Validar documento único (ignorar el de la misma persona)
        String requestDocNum = safeTrim(personRequest.getDocument_number());
        if (requestDocNum != null) {
            Optional<Person> personWithDoc = personRepository.findByDocumentNumber(requestDocNum);
            if (personWithDoc.isPresent() && !personWithDoc.get().getId().equals(id)) {
                throw new RuntimeException("El número de documento ya está registrado");
            }
        }

        existente.setUbigeo_code(safeTrim(personRequest.getUbigeo_code()));
        existente.setName(safeTrim(personRequest.getName()));
        existente.setLast_name(safeTrim(personRequest.getLast_name()));
        existente.setDocument_type(safeTrim(personRequest.getDocument_type()));
        existente.setDocument_number(requestDocNum);
        existente.setPhone(safeTrim(personRequest.getPhone()));
        existente.setEmail(requestEmail);
        existente.setRole(safeTrim(personRequest.getRole()));
        existente.setStreet(safeTrim(personRequest.getStreet()));
        // Hashear la nueva contraseña solo si fue enviada
        if (personRequest.getPassword() != null && !personRequest.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(personRequest.getPassword()));
        }
        existente.setState(safeTrim(personRequest.getState()));
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

    @Override
    public PersonRequest login(LoginRequest loginRequest) {
        Person person = personRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("El correo electrónico no existe"));

        if (!"ADMIN".equals(person.getRole())) {
            throw new RuntimeException("Acceso denegado: Solo los administradores pueden iniciar sesión");
        }

        if (!"A".equals(person.getState())) {
            throw new RuntimeException("Su cuenta está inactiva");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), person.getPassword())) {
            throw new RuntimeException("La contraseña es incorrecta");
        }

        return convertToRequest(person);
    }

   
    // La contraseña NO se incluye en la respuesta por seguridad
    private PersonRequest convertToRequest(Person person) {
        return PersonRequest.builder()
                .id(person.getId())
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

    private String safeTrim(String str) {
        return str == null ? null : str.trim();
    }
}