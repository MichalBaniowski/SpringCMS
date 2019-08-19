package pl.coderslab.repository;

import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@DaoType(type = DaoType.Type.AUTHOR)
public class AuthorDaoImpl implements Dao<Author, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Author> all() {
        TypedQuery<Author> query = entityManager.createNamedQuery("Author.allList", Author.class);
        return query.getResultList();
    }

    @Override
    public Long create(Author entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public Author retrieve(Long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public Author update(Author entity) {
        return entityManager.merge(entity);
    }

    @Override
    public boolean delete(Long id) {
        Author author = entityManager.find(Author.class, id);
        if(author == null) {
            return false;
        }
        entityManager.remove(author);
        return true;
    }
}
