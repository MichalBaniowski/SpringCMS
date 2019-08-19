package pl.coderslab.repository;

import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@DaoType(type = DaoType.Type.CATEGORY)
public class CategoryDaoImpl implements Dao<Category, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Category> all() {
        TypedQuery<Category> query = entityManager.createNamedQuery("Category.allList", Category.class);
        return query.getResultList();
    }

    @Override
    public Long create(Category entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public Category retrieve(Long id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public Category update(Category entity) {
        return entityManager.merge(entity);
    }

    @Override
    public boolean delete(Long id) {
        Category category = entityManager.find(Category.class, id);
        if (category == null) {
            return false;
        }
        entityManager.remove(category);
        return true;
    }
}
