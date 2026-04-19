package vallegrande.edu.pe.DonAlfonso.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRABAJADOR", schema = "dbo")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identificador")
    private Integer identificador;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "celular")
    private String celular;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "estado")
    private String estado;

    // Auditoria: fecha y hora de registro del trabajador.
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Auditoria: fecha y hora de la ultima edicion.
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Auditoria: fecha y hora de eliminacion logica.
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Auditoria: fecha y hora de restauracion logica.
    @Column(name = "restored_at")
    private LocalDateTime restoredAt;

    // Antes de insertar, guarda createdAt y estado por defecto.
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.estado == null || this.estado.isBlank()) {
            this.estado = "A";
        }
    }

    // Antes de actualizar, guarda la marca de tiempo de edicion.
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}