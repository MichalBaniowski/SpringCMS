package pl.coderslab.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Category;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
