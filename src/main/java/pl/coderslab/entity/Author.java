package pl.coderslab.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
@NamedQuery(name = "Author.allList", query = "SELECT a FROM Author a")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "pole nie może być puste")
    private String firstName;

    @NotBlank(message = "pole nie może być puste")
    private String lastName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Transient
    private String fullName;

    public String getFullName() {
        if(fullName == null){
            fullName = String.format("%s %s", firstName, lastName);
        }
        return fullName;
    }
}
