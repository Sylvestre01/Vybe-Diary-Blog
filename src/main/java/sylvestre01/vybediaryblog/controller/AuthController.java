package sylvestre01.vybediaryblog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sylvestre01.vybediaryblog.Security.JwtTokenProvider;
import sylvestre01.vybediaryblog.payload.JwtAuthenticationResponse;
import sylvestre01.vybediaryblog.payload.LoginRequest;
import sylvestre01.vybediaryblog.payload.SignUpRequest;
import sylvestre01.vybediaryblog.payload.UserRegistrationResponse;
import sylvestre01.vybediaryblog.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("auth/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        UserRegistrationResponse userRegistrationResponse = authService.registerUser(signUpRequest);
        return new ResponseEntity<>(userRegistrationResponse, HttpStatus.CREATED);

    }



}
