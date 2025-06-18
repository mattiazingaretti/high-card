package it.sara.demo.web.user.response;


import it.sara.demo.service.user.result.AddUserResult;
import it.sara.demo.web.response.GenericResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
public class AddUserResponse extends GenericResponse {
    private AddUserResult result;

}
