package sylvestre01.vybediaryblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sylvestre01.vybediaryblog.exception.ResourceNotFoundException;
import sylvestre01.vybediaryblog.payload.LoginRequest;
import sylvestre01.vybediaryblog.payload.SignUpPayload;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.UserRegistrationResponse;
import sylvestre01.vybediaryblog.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody SignUpPayload signUpRequest) {
        UserRegistrationResponse userRegistrationResponse = authService.registerUser(signUpRequest);
        return new ResponseEntity<>(userRegistrationResponse, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws ResourceNotFoundException {
        ApiResponse<?> apiResponse = authService.authenticateUser(loginRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}