package pl.coderslab.repository;

import pl.coderslab.entity.Article;
import pl.coderslab.entity.Author;
import pl.coderslab.entity.Category;

import java.util.List;

public interface ArticleDao extends Dao<Article, Long> {
    List<Article> getLatest5();
    List<Article> getAllByCategory(Category category);
    List<Article> getAllByAuthor(Author author);
}
