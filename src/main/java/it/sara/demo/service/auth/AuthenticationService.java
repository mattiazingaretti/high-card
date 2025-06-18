package it.sara.demo.service.auth;

import it.sara.demo.exception.GenericException;
import it.sara.demo.web.auth.request.LoginRequest;
import it.sara.demo.web.auth.request.RegisterRequest;
import it.sara.demo.web.auth.response.LoginResponse;
import it.sara.demo.web.auth.response.RegisterResponse;

public interface AuthenticationService {
    
    RegisterResponse register(RegisterRequest registerRequest) throws GenericException;

    LoginResponse login(LoginRequest loginRequest) throws GenericException;
}
