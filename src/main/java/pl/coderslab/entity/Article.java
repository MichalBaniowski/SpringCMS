package pl.coderslab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.validation.CompleteArticleGroup;
import pl.coderslab.validation.DraftArticleGroup;
import pl.coderslab.validation.NumberOfWords;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
@NamedQueries({ @NamedQuery(name = "Article.allList", query = "SELECT a FROM Article a"),
                @NamedQuery(name = "Article.latest5List", query = "SELECT a FROM Article a ORDER BY a.created DESC"),
                @NamedQuery(name = "Article.allByCategory", query = "SELECT a FROM Article a WHERE :category MEMBER OF a.categories"),
                @NamedQuery(name = "Article.allByAuthor", query = "SELECT a FROM Article a WHERE a.author = :author")})
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "pole nie może być puste", groups = {CompleteArticleGroup.class, DraftArticleGroup.class})
    @NumberOfWords(min = 3, message = "tytuł powinien składać się minimum z 3 słów", groups = {CompleteArticleGroup.class, DraftArticleGroup.class})
    @Size(max = 200, message = "tytuł nie powiniem przekroczyć 200 znaków", groups = {CompleteArticleGroup.class, DraftArticleGroup.class})
    @Column(length = 200)
    private String title;

    @NotNull(message = "musisz wybrać autora", groups = {CompleteArticleGroup.class})
    @ManyToOne
    private Author author;

    @NotEmpty(message = "musisz wybrać kategorię", groups = {CompleteArticleGroup.class})
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories = new HashSet<>();

    private boolean draft;

    @NotNull
    @Size(min = 500, message = "artykuł musi mieć minimum 500 znaków", groups = {CompleteArticleGroup.class, DraftArticleGroup.class} )
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updated;

    @PrePersist
    public void prePersist(){
        created = LocalDateTime.now();
        updated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updated = LocalDateTime.now();
    }

}
