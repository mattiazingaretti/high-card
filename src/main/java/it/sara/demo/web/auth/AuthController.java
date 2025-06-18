package it.sara.demo.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sara.demo.exception.GenericException;
import it.sara.demo.service.auth.AuthenticationService;
import it.sara.demo.web.auth.request.LoginRequest;
import it.sara.demo.web.auth.request.RegisterRequest;
import it.sara.demo.web.auth.response.LoginResponse;
import it.sara.demo.web.auth.response.RegisterResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthenticationService authenticationSercice;

    /**
     * Endpoint per la creazione del token di autenticazione
     * @param request
     * @return
     * @throws GenericException
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> createAuthenticationToken(@RequestBody @Valid LoginRequest request) throws GenericException {
        return ResponseEntity.ok(authenticationSercice.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) throws GenericException{
        return ResponseEntity.ok(authenticationSercice.register(request));
    }
}