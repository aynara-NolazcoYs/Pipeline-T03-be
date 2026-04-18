package vallegrande.edu.pe.DonAlfonso.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
}