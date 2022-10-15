package sylvestre01.vybediaryblog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sylvestre01.vybediaryblog.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Page<Photo> findByAlbumId(Long albumId, Pageable pageable);
}
