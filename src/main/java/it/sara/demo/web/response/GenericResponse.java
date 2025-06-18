package it.sara.demo.web.response;

import it.sara.demo.dto.StatusDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@SuperBuilder
@Getter
public class GenericResponse {
    private StatusDTO status;


    public static GenericResponse success(String message) {
        return new GenericResponse(
                new StatusDTO(200, message != null ? message : "Success", java.util.UUID.randomUUID().toString())
        );
    }


}
