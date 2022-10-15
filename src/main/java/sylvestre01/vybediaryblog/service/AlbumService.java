package sylvestre01.vybediaryblog.service;

import org.springframework.http.ResponseEntity;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.Album;
import sylvestre01.vybediaryblog.payload.AlbumRequest;
import sylvestre01.vybediaryblog.payload.AlbumResponse;
import sylvestre01.vybediaryblog.payload.ApiResponse;
import sylvestre01.vybediaryblog.payload.PagedResponse;

public interface AlbumService {
    PagedResponse<AlbumResponse> getAllAlbums(int page, int size);

    ResponseEntity<Album> addAlbum(AlbumRequest albumRequest, UserPrincipal currentUser);

    ResponseEntity<Album> getAlbum(Long id);

    ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumRequest newAlbum, UserPrincipal currentUser);

    ResponseEntity<ApiResponse> deleteAlbum(Long id, UserPrincipal currentUser);

    PagedResponse<Album> getUserAlbums(String username, int page, int size);
}
