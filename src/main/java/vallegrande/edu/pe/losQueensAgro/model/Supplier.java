package vallegrande.edu.pe.losQueensAgro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier")
@Data
public class Supplier {

    // Campo 1: ID del proveedor (Long)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Campo 2: Nombre comercial del proveedor (String)
    @Column(name = "commercial_name", length = 150)
    @NotBlank(message = "El nombre comercial es obligatorio")
    @Size(max = 150, message = "El nombre comercial no puede superar los 150 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s-]+$", message = "El nombre comercial solo debe contener letras, espacios y guiones (-)")
    private String commercialName;

    // Campo 3: Teléfono de contacto (String)
    @Column(length = 9)
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\d{9}$", message = "El teléfono debe tener exactamente 9 dígitos y contener solo números")
    private String phone;

    // Campo 4: Email del proveedor (String)
    @Column(length = 150)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Ingrese un correo electrónico válido")
    @Size(max = 150, message = "El correo electrónico no puede superar los 150 caracteres")
    private String email;

    // Campo 5: Código UBIGEO de ubicación (String)
    @Column(name = "ubigeo_code", length = 6)
    @NotBlank(message = "El código de ubigeo es obligatorio")
    @Pattern(regexp = "^\\d{6}$", message = "El código de ubigeo debe tener exactamente 6 dígitos y contener solo números")
    private String ubigeoCode;

    // Campo 6: Número de RUC del proveedor (String)
    @Column(name = "ruc", length = 11)
    @NotBlank(message = "El RUC es obligatorio")
    @Pattern(regexp = "^\\d{11}$", message = "El RUC debe tener exactamente 11 dígitos y contener solo números")
    private String ruc;

    // Campo 7: Dirección del proveedor (String)
    @Column(name = "address", length = 200)
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
    private String address;

    // Campo 8: Estado del proveedor (Boolean) - true: Activo, false: Inactivo
    @Column(name = "is_active")
    private Boolean isActive;

    // CAMPOS DE AUDITORÍA

    // Campo 9: Fecha de creación del proveedor (LocalDateTime)
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    // Campo 10: Fecha de actualización del proveedor (LocalDateTime)
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    // Campo 11: Fecha de eliminación del proveedor (LocalDateTime)
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    // Campo 12: Fecha de restauración del proveedor (LocalDateTime)
    @Column(name = "restored_date")
    private LocalDateTime restoredDate;
}