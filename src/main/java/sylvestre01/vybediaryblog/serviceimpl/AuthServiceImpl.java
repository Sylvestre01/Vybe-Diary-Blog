package sylvestre01.vybediaryblog.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.JwtTokenProvider;
import sylvestre01.vybediaryblog.exception.ResourceNotFoundException;
import sylvestre01.vybediaryblog.model.role.Role;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.payload.*;
import sylvestre01.vybediaryblog.repository.UserRepository;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.LoginResponse;
import sylvestre01.vybediaryblog.response.UserRegistrationResponse;
import sylvestre01.vybediaryblog.service.AuthService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserRegistrationResponse registerUser(SignUpPayload signUpRequest) {
        String email = signUpRequest.getEmail();

        boolean existingUser = userRepository.existsByEmail(email);
        if (!existingUser) {
            User user = new User();
            Role role = user.getRole();
            user.setFirstName(signUpRequest.getFirstName().toLowerCase());
            user.setLastName(signUpRequest.getLastName().toLowerCase());
            user.setUsername(signUpRequest.getUsername().toLowerCase());
            user.setEmail(signUpRequest.getEmail().toLowerCase());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setRole(role);
            userRepository.save(user);

            return new UserRegistrationResponse("success", LocalDateTime.now());

        } else {
            throw new ResourceNotFoundException("user already exists");
        }
    }
    @Override
    public ApiResponse<LoginResponse> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication= authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User loggedInUser = userRepository.findByUsernameOrEmail(loginRequest.getEmail(), loginRequest.getEmail()).get();
        if(loggedInUser.getRole().equals(Role.USER) || loggedInUser.getRole().equals(Role.ADMIN)) {
            return new ApiResponse<>("success", LocalDateTime.now(), new LoginResponse(jwtTokenProvider.generateToken(loginRequest.getEmail())));
        }
        return new ApiResponse<>("unsuccessful", LocalDateTime.now());
    }
}
