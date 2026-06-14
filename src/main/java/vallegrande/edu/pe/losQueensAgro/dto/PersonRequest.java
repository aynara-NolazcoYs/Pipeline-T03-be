package vallegrande.edu.pe.losQueensAgro.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRequest {

    private Long id; 
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-záéíóúñA-ZÁÉÍÓÚÑ\\s]+$", message = "El nombre solo debe contener letras y espacios, sin números ni caracteres especiales")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-záéíóúñA-ZÁÉÍÓÚÑ\\s]+$", message = "El apellido solo debe contener letras y espacios, sin números ni caracteres especiales")
    private String last_name;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Pattern(regexp = "DNI|RUC|PASAPORTE|CARNET", message = "Tipo de documento no válido (DNI, RUC, PASAPORTE, CARNET)")
    private String document_type;

    @NotBlank(message = "El número de documento es obligatorio")
    @Pattern(regexp = "^[0-9]{8}$|^[0-9]{12}$", message = "El número de documento debe contener 8 dígitos para DNI o 12 para CARNET")
    private String document_number;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El formato del correo electrónico no es válido")
    private String email;

    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe contener 9 dígitos")
    private String phone;

    @NotBlank(message = "El código UBIGEO es obligatorio")
    @Pattern(regexp = "^[0-9]{6}$", message = "El código UBIGEO debe contener 6 dígitos")
    private String ubigeo_code;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "ADMIN|VENDEDOR|ALMACENERO|CLIENTE|PROVEEDOR", message = "Rol no válido")
    private String role;

    @Size(max = 150, message = "La calle no debe exceder 150 caracteres")
    private String street;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

    @Pattern(regexp = "^[AI]$", message = "El estado debe ser A (activo) o I (inactivo)")
    private String state;
}
