package pl.coderslab.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Author;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
