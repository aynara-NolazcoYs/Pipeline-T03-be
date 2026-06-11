package vallegrande.edu.pe.losQueensAgro.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data                     //Generar los getters y setters
@Table(name= "category")   //Nombre en la tabla en la base de datos
public class Category {

    @Id
    @Column(name = "id") //Nombre del campo en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Para que el ID se genere de manera automatica
    private Long id; //Nombre del atributo en la clase java
    
    @Column(name = "name")
    private String name; //Nombre del atributo en la clase java

    @Column(name = "description")
    private String description; //Nombre del atributo en la clase java

    @Column(name = "status")
    private String status; //Nombre del atributo en la clase java
}
