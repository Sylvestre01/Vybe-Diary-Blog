package sylvestre01.vybediaryblog.serviceimpl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.exception.ResourceNotFoundException;
import sylvestre01.vybediaryblog.model.role.Role;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.payload.SignUpRequest;
import sylvestre01.vybediaryblog.payload.UserRegistrationResponse;
import sylvestre01.vybediaryblog.repository.UserRepository;
import sylvestre01.vybediaryblog.service.AuthService;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    @Override
    public UserRegistrationResponse registerUser(SignUpRequest signUpRequest) {
        String email = signUpRequest.getUsername();

        Boolean existingUser = userRepository.existsByUsername(email);
        if (existingUser == null) {
            User user = new User();

            user.setFirstName(signUpRequest.getFirstName().toLowerCase());
            user.setLastName(signUpRequest.getLastName().toLowerCase());
            user.setUsername(signUpRequest.getUsername().toLowerCase());
            user.setEmail(signUpRequest.getEmail().toLowerCase());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setRole(user.getRole());
            userRepository.save(user);

            return new UserRegistrationResponse("success", LocalDateTime.now());

        } else {
            return new UserRegistrationResponse("unsuccessful registration", LocalDateTime.now());
        }
    }
}
