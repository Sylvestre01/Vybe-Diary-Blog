package sylvestre01.vybediaryblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sylvestre01.vybediaryblog.Security.CurrentUser;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.payload.PhotoPayload;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.response.PhotoResponse;
import sylvestre01.vybediaryblog.service.PhotoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/photos")
public class PhotoController {

    private PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping
    public PagedResponse<PhotoResponse> getAllPhotos(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size) {
        return photoService.getAllPhotos(page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PhotoResponse> addPhoto(@Valid @RequestBody PhotoPayload photoPayload, @CurrentUser UserPrincipal currentUser) {
        PhotoResponse photoResponse = photoService.addPhoto(photoPayload, currentUser);
        return new ResponseEntity<>(photoResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoResponse> getPhoto(@PathVariable(name = "id") Long id) {
        PhotoResponse photoResponse = photoService.getPhoto(id);
        return new ResponseEntity<>(photoResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PhotoResponse> updatePhoto(@PathVariable(name = "id") Long id,
                                                     @Valid @RequestBody PhotoPayload photoPayload, @CurrentUser UserPrincipal currentUser) {
        PhotoResponse photoResponse = photoService.updatePhoto(id, photoPayload, currentUser);
        return new ResponseEntity<>(photoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deletePhoto(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = photoService.deletePhoto(id, currentUser);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
}


}
