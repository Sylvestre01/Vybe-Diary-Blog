package sylvestre01.vybediaryblog.service;

import org.springframework.http.ResponseEntity;
import sylvestre01.vybediaryblog.payload.*;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.AuthenticationResponse;
import sylvestre01.vybediaryblog.response.UserRegistrationResponse;

public interface AuthService {
    UserRegistrationResponse registerUser(SignUpPayload signUpRequest);

    AuthenticationResponse authenticateUser(LoginRequest loginRequest);




}
