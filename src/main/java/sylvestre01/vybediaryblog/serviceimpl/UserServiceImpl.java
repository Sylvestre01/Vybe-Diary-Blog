package sylvestre01.vybediaryblog.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.exception.*;
import sylvestre01.vybediaryblog.model.role.Role;
import sylvestre01.vybediaryblog.model.user.Address;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.payload.*;
import sylvestre01.vybediaryblog.repository.PostRepository;
import sylvestre01.vybediaryblog.repository.UserRepository;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.service.UserService;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PostRepository postRepository;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserSummaryPayload getCurrentUser(UserPrincipal currentUser) {
        return new UserSummaryPayload(currentUser.getId(), currentUser.getUsername(), currentUser.getFirstName(),
        currentUser.getLastName());
    }

    @Override
    public User addUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            ApiResponse apiResponse = new ApiResponse("Username is already taken", LocalDateTime.now());
            throw new BadRequestException(apiResponse);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            ApiResponse apiResponse = new ApiResponse("Email is already taken", LocalDateTime.now());
            throw new BadRequestException(apiResponse);
        }
        user.setRole(user.getRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public ApiResponse deleteUser(String username, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username cannot be found"));
        if (!user.getId().equals(currentUser.getId()) || !currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            ApiResponse apiResponse = new ApiResponse("You don't have permission to delete profile of: " + username, LocalDateTime.now());
            throw new AccessDeniedException(apiResponse);
        }

        userRepository.deleteById(user.getId());

        return new ApiResponse("You successfully deleted profile of: " + username, LocalDateTime.now());
    }

    @Override
    public User updateUser(User newUser, String username, UserPrincipal currentUser) {
        User user = userRepository.getUserByName(username);
        if(user.getId().equals(currentUser.getId())
        || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setPassword(newUser.getPassword());
            user.setAddress(newUser.getAddress());
            user.setPhone(newUser.getPhone());
            user.setWebsite(newUser.getWebsite());
            return userRepository.save(user);
        }
        ApiResponse apiResponse = new ApiResponse("You don't have permission to update profile of: " + username, LocalDateTime.now(), HttpStatus.UNAUTHORIZED);
        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public UserIdentityAvailability checkUsernameAvailability(String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @Override
    public UserIdentityAvailability checkEmailAvailability(String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @Override
    public UserProfilePayload getUserProfile(String username) {
        User user = userRepository.getUserByName(username);
        Long postCount = postRepository.countByCreatedBy(user.getId());

        return new UserProfilePayload(user.getId(), user.getUsername(), user.getFirstName(),
                user.getLastName(), Instant.now(), user.getEmail(),
                user.getAddress(), user.getPhone(), user.getWebsite(), postCount);
    }

    @Override
    public ApiResponse giveAdmin(String username) {
        User user = userRepository.getUserByName(username);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return new ApiResponse("You gave ADMIN role from: " + username, LocalDateTime.now());

    }

    @Override
    public ApiResponse removeAdmin(String username) {
        User user = userRepository.getUserByName(username);
        user.setRole(Role.USER);
        userRepository.save(user);

        return new ApiResponse("you took ADMIN role from " + username, LocalDateTime.now());
    }

    @Override
    public UserProfilePayload setOrUpdateInfo(UserPrincipal currentUser, InfoPayload infoRequest) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException("Username cannot be found"));
        Address address = new Address(infoRequest.getStreet(), infoRequest.getSuite(), infoRequest.getCity(), infoRequest.getZipcode(), user);
        if(user.getId().equals(currentUser.getId())
            || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            user.setAddress(address);
            user.setWebsite(infoRequest.getWebsite());
            user.setPhone(infoRequest.getPhone());
            User updatedUser = userRepository.save(user);

            Long postCount = postRepository.countByCreatedBy(updatedUser.getId());

            return new UserProfilePayload(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getFirstName(),
                    updatedUser.getLastName(), Instant.now(), updatedUser.getEmail(), updatedUser.getAddress(),
                    updatedUser.getPhone(), updatedUser.getWebsite(), postCount);
        }
        ApiResponse apiResponse = new ApiResponse("You don't have permission to update users profile", LocalDateTime.now(), HttpStatus.FORBIDDEN);
        throw new AccessDeniedException(apiResponse);
    }
}




