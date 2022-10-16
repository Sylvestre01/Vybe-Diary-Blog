package sylvestre01.vybediaryblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sylvestre01.vybediaryblog.Security.CurrentUser;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.Album;
import sylvestre01.vybediaryblog.model.Post;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.payload.*;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.service.AlbumService;
import sylvestre01.vybediaryblog.service.PostService;
import sylvestre01.vybediaryblog.service.UserService;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    private PostService postService;

    private AlbumService albumService;

    @Autowired
    public UserController(UserService userService, PostService postService, AlbumService albumService) {
        this.userService = userService;
        this.postService = postService;
        this.albumService = albumService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserSummaryPayload> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummaryPayload userSummary = userService.getCurrentUser(currentUser);
        return new ResponseEntity<>(userSummary, HttpStatus.OK);
    }

    @GetMapping("/checkUsernameAvailability")
    public ResponseEntity<UserIdentityAvailability> checkEmailAvailability(@RequestParam(value = "email") String email) {
        UserIdentityAvailability userIdentityAvailability = userService.checkEmailAvailability(email);
        return new ResponseEntity<>(userIdentityAvailability, HttpStatus.OK);
    }

    @GetMapping("/checkEmailAvailability")
    public ResponseEntity<UserIdentityAvailability> checkUsernameAvailability(@RequestParam(value = "username") String username) {
        UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(username);
        return new ResponseEntity<>(userIdentityAvailability, HttpStatus.OK);
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserProfilePayload> getUserProfile(@PathVariable(value = "username") String username) {
        UserProfilePayload userProfile = userService.getUserProfile(username);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @GetMapping("/{username}/posts")
    public ResponseEntity<PagedResponse<Post>> getPostsCreatedBy(@PathVariable(value = "username") String username,
                                                                 @RequestParam(value = "page") Integer page,
                                                                 @RequestParam(value = "size") Integer size) {
        PagedResponse<Post> response = postService.getPostsByCreatedBy(username, page, size);
        return new ResponseEntity<  >(response, HttpStatus.OK);
    }

    @GetMapping("/{username}/albums")
    public ResponseEntity<PagedResponse<Album>> getUserAlbums(@PathVariable(name = "username") String username,
                                                              @RequestParam(name = "page") Integer page,
                                                              @RequestParam(name = "size") Integer size) {
        PagedResponse<Album> response = albumService.getUserAlbums(username, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User user1 = userService.addUser(user);
        return  new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User newUser,
                                           @PathVariable(value = "username") String username, @CurrentUser UserPrincipal currentUser) {
        User updatedUser = userService.updateUser(newUser, username, currentUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "username") String username,
                                                  @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = userService.deleteUser(username,currentUser);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}/giveAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> giveAdmin(@PathVariable(name = "username") String username) {
        ApiResponse apiResponse = userService.giveAdmin(username);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}/takeAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> removeAdmin(@PathVariable(name = "username") String username) {
        ApiResponse apiResponse = userService.removeAdmin(username);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    public ResponseEntity<UserProfilePayload> setAddress(@CurrentUser UserPrincipal currentUser,
                                                         @Valid @RequestBody InfoPayload infoRequest) {
        UserProfilePayload userProfile = userService.setOrUpdateInfo(currentUser, infoRequest);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }
}
