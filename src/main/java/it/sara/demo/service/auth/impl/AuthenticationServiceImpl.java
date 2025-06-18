package it.sara.demo.service.auth.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.sara.demo.dto.StatusDTO;
import it.sara.demo.exception.GenericException;
import it.sara.demo.security.JwtUtil;
import it.sara.demo.service.auth.AuthenticationService;
import it.sara.demo.service.database.UserRepository;
import it.sara.demo.service.database.model.User;
import it.sara.demo.web.auth.request.LoginRequest;
import it.sara.demo.web.auth.request.RegisterRequest;
import it.sara.demo.web.auth.response.LoginResponse;
import it.sara.demo.web.auth.response.RegisterResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws GenericException {

        try {
            User user = modelMapper.map(registerRequest, User.class);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); //Cruciale per non salvare psw in chiaro a DB 
            
            if(userRepository.getByEmail(user.getEmail()).isPresent()) {
                log.error("User with email {} already exists", user.getEmail());
                throw new GenericException(GenericException.USER_ALREADY_EXISTS);
            }
                
            String guid = userRepository.save(user);

            log.info("User registered successfully with GUID: {}", guid);
            return RegisterResponse.builder()
                    .userId(guid)
                    .status(StatusDTO.success("User added successfully"))
                    .build();
        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage(), e);
            if(e instanceof GenericException) {
                throw e;
            }
            throw new GenericException(GenericException.REGISTRATION_ERROR); 
        }
    }


    @Override
    public LoginResponse login(LoginRequest request) throws GenericException {
        
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);
            return LoginResponse.builder()
                    .jwt(jwt)
                    .status(StatusDTO.success("Login Successful"))
                    .build();
        }catch (Exception e) {
            log.error("Error during login: {}", e.getMessage(), e);
            throw new GenericException(GenericException.LOGIN_ERROR);
        }
    }
    
}
