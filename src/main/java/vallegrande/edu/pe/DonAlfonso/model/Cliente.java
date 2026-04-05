package vallegrande.edu.pe.DonAlfonso.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CLIENTE")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identificador")
    private Integer identificador;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no debe superar 150 caracteres")
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "^\\d{9}$", message = "El celular debe tener 9 digitos")
    @Column(name = "celular", nullable = false, length = 9)
    private String celular;

    @NotBlank(message = "El correo es obligatorio")
    @Size(max = 150, message = "El correo no debe superar 150 caracteres")
    @Column(name = "correo", nullable = false, length = 150)
    private String correo;

    @NotBlank(message = "El ruc es obligatorio")
    @Pattern(regexp = "^\\d{11}$", message = "El RUC debe tener 11 digitos")
    @Column(name = "ruc", nullable = false, length = 11)
    private String ruc;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 200, message = "La direccion no debe superar 200 caracteres")
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @Pattern(regexp = "^[AI]$", message = "El estado debe ser A o I")
    @Column(name = "estado", nullable = false, length = 1)
    private String estado;
}
