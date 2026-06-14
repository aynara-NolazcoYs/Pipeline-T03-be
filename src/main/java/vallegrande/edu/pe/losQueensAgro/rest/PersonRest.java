package vallegrande.edu.pe.losQueensAgro.rest;

import vallegrande.edu.pe.losQueensAgro.dto.PersonRequest;
import vallegrande.edu.pe.losQueensAgro.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vallegrande.edu.pe.losQueensAgro.service.PersonService;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/person") //Ruta base para acceder a los endpoints relacionados con personas
@Tag(name = "Person API", description = "Endpoints para gestionar personas")

public class PersonRest {
     //Inyección del  service
    private final PersonService personService;

    @Autowired
    public PersonRest(PersonService personService) {
        this.personService = personService;
    }

    //Mapear endpoint Listar Todos - tipo GET en POSTMAN
    @GetMapping
    @Operation(summary = "Get All Person", description = "Get All Person")
    public ResponseEntity<List<PersonRequest>> findAll(){
        return ResponseEntity.ok(personService.findAll());
    }

    //Mapear Endpoint Listar por estado - tipo GET en POSTMAN
    @GetMapping("/state/{state}")
    @Operation(summary = "Get Person By STATE", description = "Get Person By STATE")
    public ResponseEntity<List<PersonRequest>> findBystate(@PathVariable String state) {
        return ResponseEntity.ok(personService.findByState(state));
    }

    //Mapear Endpoint Listar por ID - tipo GET en POSTMAN
    @GetMapping("/{id}")
    @Operation(summary = "Get Person By ID", description = "Get Person By ID")
    public ResponseEntity<PersonRequest> findById(@PathVariable Long id) {
        Optional<PersonRequest> person = personService.findById(id);
        return person.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Mapperar Endpoint Registrar - tipo POST en POSTMAN
    @PostMapping("/save")
    @Operation(summary = "Save Person", description = "Save Person with validation")
    public ResponseEntity<?> save(@Valid @RequestBody PersonRequest personRequest) {
        try {
            PersonRequest saved = personService.save(personRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }

    //Mapear Endpoint Actualizar - tipo PUT en POSTMAN
    @PutMapping("/update/{id}")
    @Operation(summary = "Update Person", description = "Update Person with validation")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PersonRequest personRequest) {
        try {
            PersonRequest updated = personService.update(id, personRequest);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }

    //Mapear Endpoint Eliminar (Cambio de Estado) por ID - tipo PATCH en POSTMAN
    @PatchMapping("/delete/{id}")
    @Operation(summary = "Logical Delete Person", description = "Logical Delete Person")
    public ResponseEntity<PersonRequest> delete(@PathVariable Long id) {
        try {
            PersonRequest deleted = personService.delete(id);
            return ResponseEntity.ok(deleted);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Mapear endpoint Restaurar (Cambio de Estado) por ID - tipo PATCH en POSTMAN
    @PatchMapping("/restore/{id}")
    @Operation(summary = "Logical Restore Person", description = "Logical Restore Person")
    public ResponseEntity<PersonRequest> restore(@PathVariable Long id){
        try {
            PersonRequest restored = personService.restore(id);
            return ResponseEntity.ok(restored);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Mapear Endpoint Login - tipo POST en POSTMAN
    @PostMapping("/login")
    @Operation(summary = "Login Person", description = "Validate user credentials (only active ADMIN users)")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            PersonRequest response = personService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }

}

