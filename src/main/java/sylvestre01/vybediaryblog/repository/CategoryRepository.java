package sylvestre01.vybediaryblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sylvestre01.vybediaryblog.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
