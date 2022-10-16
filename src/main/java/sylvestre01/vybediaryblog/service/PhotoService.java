package sylvestre01.vybediaryblog.service;

import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.payload.PhotoPayload;
import sylvestre01.vybediaryblog.response.PhotoResponse;

public interface PhotoService {

    PagedResponse<PhotoResponse> getAllPhotos(int page, int size);

    PhotoResponse getPhoto(Long id);

    PhotoResponse updatePhoto(Long id, PhotoPayload photoRequest, UserPrincipal currentUser);

    PhotoResponse addPhoto(PhotoPayload photoRequest, UserPrincipal currentUser);

    ApiResponse deletePhoto(Long id, UserPrincipal currentUser);

    PagedResponse<PhotoResponse> getAllPhotosByAlbum(Long albumId, int page, int size);

}
