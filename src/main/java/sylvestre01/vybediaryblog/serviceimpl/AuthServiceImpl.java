package sylvestre01.vybediaryblog.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.JwtTokenProvider;
import sylvestre01.vybediaryblog.exception.ResourceNotFoundException;
import sylvestre01.vybediaryblog.model.role.Role;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.payload.*;
import sylvestre01.vybediaryblog.repository.UserRepository;
import sylvestre01.vybediaryblog.response.AuthenticationResponse;
import sylvestre01.vybediaryblog.response.UserRegistrationResponse;
import sylvestre01.vybediaryblog.service.AuthService;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {


    private UserRepository userRepository;


    private JwtTokenProvider jwtTokenProvider;

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
    public AuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        AuthenticationManager authentication;
        try {
            authentication = (AuthenticationManager) authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException("invalid username or password");
        }
        SecurityContextHolder.getContext().setAuthentication((Authentication) authentication);

        String jwt = jwtTokenProvider.generateToken(String.valueOf(authentication));
        return new AuthenticationResponse(jwt);
    }


}
