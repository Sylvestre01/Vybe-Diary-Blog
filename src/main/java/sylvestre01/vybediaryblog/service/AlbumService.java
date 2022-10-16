package sylvestre01.vybediaryblog.service;

import org.springframework.http.ResponseEntity;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.Album;
import sylvestre01.vybediaryblog.payload.AlbumPayload;
import sylvestre01.vybediaryblog.response.AlbumResponse;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;

public interface AlbumService {
    PagedResponse<AlbumResponse> getAllAlbums(int page, int size);

    ResponseEntity<Album> addAlbum(AlbumPayload albumRequest, UserPrincipal currentUser);

    ResponseEntity<Album> getAlbum(Long id);

    ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumPayload newAlbum, UserPrincipal currentUser);

    ResponseEntity<ApiResponse> deleteAlbum(Long id, UserPrincipal currentUser);

    PagedResponse<Album> getUserAlbums(String username, int page, int size);
}
