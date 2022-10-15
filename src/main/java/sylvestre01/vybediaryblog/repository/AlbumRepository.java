package sylvestre01.vybediaryblog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sylvestre01.vybediaryblog.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    Page<Album> findByCreatedBy(Long id, Pageable pageable);
}
