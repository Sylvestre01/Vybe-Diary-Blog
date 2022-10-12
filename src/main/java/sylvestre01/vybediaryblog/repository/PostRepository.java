package sylvestre01.vybediaryblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sylvestre01.vybediaryblog.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Long countByCreatedBy(Long userId);
}
