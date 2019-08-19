package pl.coderslab.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Article;
import pl.coderslab.entity.Author;
import pl.coderslab.entity.Category;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByCategories(Category category);
    List<Article> findByAuthor(Author author);
    List<Article> findFirst5ByOrderByCreatedDesc();
    List<Article> findAllByDraft(boolean draft);
    void deleteAllByAuthor(Author author);
}
