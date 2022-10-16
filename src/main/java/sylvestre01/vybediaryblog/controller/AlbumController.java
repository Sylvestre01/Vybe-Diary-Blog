package sylvestre01.vybediaryblog.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sylvestre01.vybediaryblog.Security.CurrentUser;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.Album;
import sylvestre01.vybediaryblog.payload.AlbumPayload;
import sylvestre01.vybediaryblog.response.AlbumResponse;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.response.PhotoResponse;
import sylvestre01.vybediaryblog.service.AlbumService;
import sylvestre01.vybediaryblog.service.PhotoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private AlbumService albumService;
    private PhotoService photoService;

    @Autowired
    public AlbumController(AlbumService albumService, PhotoService photoService) {
        this.albumService = albumService;
        this.photoService = photoService;
    }

//    @ExceptionHandler(ResponseEntityErrorException.class)
//    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
//        return exception.getApiResponse();
//    }

    @GetMapping
    public PagedResponse<AlbumResponse> getAllAlbums(@RequestParam(name = "page") Integer page,
                                                     @RequestParam(name = "size") Integer size) {
        return albumService.getAllAlbums(page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Album> addAlbum(@Valid @RequestBody AlbumPayload albumPayload, @CurrentUser UserPrincipal currentUser) {
        return albumService.addAlbum(albumPayload, currentUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbum(@PathVariable(name = "id") Long id) {
        return albumService.getAlbum(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AlbumResponse> updateAlbum(@PathVariable(name = "id") Long id, @Valid @RequestBody AlbumPayload albumPayload,
                                                     @CurrentUser UserPrincipal currentUser) {
        return albumService.updateAlbum(id, albumPayload, currentUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteAlbum(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        return albumService.deleteAlbum(id, currentUser);
    }

    @GetMapping("/{id}/photos")
    public ResponseEntity<PagedResponse<PhotoResponse>> getAllPhotosByAlbum(@PathVariable(name = "id") Long id,
                                                                            @RequestParam(name = "page") Integer page,
                                                                            @RequestParam(name = "size") Integer size) {

        PagedResponse<PhotoResponse> response = photoService.getAllPhotosByAlbum(id, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
