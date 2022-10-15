package sylvestre01.vybediaryblog.service;

import sylvestre01.vybediaryblog.payload.SignUpRequest;
import sylvestre01.vybediaryblog.payload.UserRegistrationResponse;

public interface AuthService {
    UserRegistrationResponse registerUser(SignUpRequest signUpRequest);




}
