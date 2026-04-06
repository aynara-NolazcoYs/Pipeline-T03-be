package vallegrande.edu.pe.DonAlfonso.model;

import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDate;

@Entity
@Table(name = "TRABAJADOR")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identificador;

    private String nombre;
    private String celular;
    private String cargo;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    private String estado;
}