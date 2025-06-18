package it.sara.demo.web.auth.request;

import it.sara.demo.web.request.GenericRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest extends GenericRequest{
    private String username;
    private String password;
}
