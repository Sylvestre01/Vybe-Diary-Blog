package sylvestre01.vybediaryblog.service;

import sylvestre01.vybediaryblog.payload.*;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.LoginResponse;
import sylvestre01.vybediaryblog.response.UserRegistrationResponse;

public interface AuthService {
    UserRegistrationResponse registerUser(SignUpPayload signUpRequest);

    ApiResponse<LoginResponse> authenticateUser(LoginRequest loginRequest);




}
