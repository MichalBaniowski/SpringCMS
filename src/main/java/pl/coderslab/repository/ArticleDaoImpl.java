package pl.coderslab.repository;

import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Article;
import pl.coderslab.entity.Author;
import pl.coderslab.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
@DaoType(type = DaoType.Type.ARTICLE)
public class ArticleDaoImpl implements ArticleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Article> all() {
        TypedQuery<Article> query = entityManager.createNamedQuery("Article.allList", Article.class);
        return query.getResultList();
    }

    @Override
    public List<Article> getLatest5() {
        TypedQuery<Article> query = entityManager.createNamedQuery("Article.latest5List", Article.class).setMaxResults(5);
        return query.getResultList();
    }

    @Override
    public List<Article> getAllByCategory(Category category) {
        TypedQuery<Article> query = entityManager.createNamedQuery("Article.allByCategory", Article.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public List<Article> getAllByAuthor(Author author) {
        TypedQuery<Article> query = entityManager.createNamedQuery("Article.allByAuthor", Article.class);
        query.setParameter("author", author);
        return query.getResultList();
    }

    @Override
    public Long create(Article entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public Article retrieve(Long id) {
        return entityManager.find(Article.class, id);
    }

    @Override
    public Article update(Article entity) {
        return entityManager.merge(entity);
    }

    @Override
    public boolean delete(Long id) {
        Article article = entityManager.find(Article.class, id);
        if(article == null) {
            return false;
        }
        entityManager.remove(article);
        return true;
    }
}
