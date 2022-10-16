package sylvestre01.vybediaryblog.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.exception.BlogapiException;
import sylvestre01.vybediaryblog.exception.ResourceNotFoundException;
import sylvestre01.vybediaryblog.model.Album;
import sylvestre01.vybediaryblog.model.role.Role;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.payload.AlbumPayload;
import sylvestre01.vybediaryblog.response.AlbumResponse;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.repository.AlbumRepository;
import sylvestre01.vybediaryblog.repository.UserRepository;
import sylvestre01.vybediaryblog.service.AlbumService;
import sylvestre01.vybediaryblog.utils.AppUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private AlbumRepository albumRepository;


    private UserRepository userRepository;


    private ModelMapper modelMapper;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, UserRepository userRepository) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PagedResponse<AlbumResponse> getAllAlbums(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Album> albums = albumRepository.findAll(pageable);

        if (albums.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), albums.getNumber(), albums.getSize(), albums.getTotalElements(),
                    albums.getTotalPages(), albums.isLast());
        }

        List<AlbumResponse> albumResponses = Arrays.asList(modelMapper.map(albums.getContent(), AlbumResponse[].class));

        return new PagedResponse<>(albumResponses, albums.getNumber(), albums.getSize(), albums.getTotalElements(), albums.getTotalPages(),
                albums.isLast());
    }

    @Override
    public ResponseEntity<Album> addAlbum(AlbumPayload albumRequest, UserPrincipal currentUser) {
        User user = userRepository.getUser(currentUser);
        Album album = new Album();
        modelMapper.map(albumRequest, album);
        album.setUser(user);
        Album newAlbum =albumRepository.save(album);

        return new ResponseEntity<>(newAlbum, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Album> getAlbum(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("album id was not found"));
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumPayload newAlbum, UserPrincipal currentUser) {
        Album album = albumRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("album id not found"));
        User user = userRepository.getUser(currentUser);
        if (album.getUser().getId().equals(user.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            album.setTitle(newAlbum.getTitle());
            Album updatedAlbum = albumRepository.save(album);

            AlbumResponse albumResponse = new AlbumResponse();

            modelMapper.map(updatedAlbum, albumResponse);

            return new ResponseEntity<>(albumResponse, HttpStatus.OK);
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED, "You don't have permission to make this operation");

    }

    @Override
    public ResponseEntity<ApiResponse> deleteAlbum(Long id, UserPrincipal currentUser) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("album id cannot be found"));
        User user = userRepository.getUser(currentUser);
        if (album.getUser().getId().equals(user.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            albumRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse("You successfully deleted album", LocalDateTime.now()), HttpStatus.OK);
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED, "You don't have permission to make this operation");

    }

    @Override
    public PagedResponse<Album> getUserAlbums(String username, int page, int size) {
        User user = userRepository.getUserByName(username);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "created at");

        Page<Album> albums = albumRepository.findByCreatedBy(user.getId(), pageable);

        List<Album> content = albums.getNumberOfElements() > 0 ? albums.getContent() : Collections.emptyList();

        return new PagedResponse<>(content, albums.getNumber(), albums.getSize(), albums.getTotalElements(), albums.getTotalPages(), albums.isLast());
    }
}

