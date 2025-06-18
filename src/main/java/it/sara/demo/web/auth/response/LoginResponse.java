package it.sara.demo.web.auth.response;

import it.sara.demo.web.response.GenericResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
public class LoginResponse extends GenericResponse{
    private String jwt;
}
