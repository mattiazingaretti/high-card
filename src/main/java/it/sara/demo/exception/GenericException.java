package it.sara.demo.exception;

import it.sara.demo.dto.StatusDTO;
import lombok.Getter;

@Getter
public class GenericException extends Exception {


    public final static StatusDTO GENERIC_ERROR = StatusDTO.builder().code(500).message("Generic Error").build();
    public final static StatusDTO USER_SAVE_ERROR = StatusDTO.builder().code(500001).message("User save error").build();
    public final static StatusDTO REGISTRATION_ERROR = StatusDTO.builder().code(500002).message("Failed to register user").build();
    public final static StatusDTO LOGIN_ERROR = StatusDTO.builder().code(500003).message("Failed to login user").build();
    public final static StatusDTO USER_ALREADY_EXISTS = StatusDTO.builder().code(500004).message("User Already exists").build();

    private final StatusDTO status;

    public GenericException(StatusDTO status) {
        this.status = status;
    }

    public GenericException(int code, String message) {
        this.status = createStatus(code, message);
    }

    private StatusDTO createStatus(int code, String message) {
        return StatusDTO.builder()
                .code(code)
                .message(message != null ? message : "Error")
                .traceId(java.util.UUID.randomUUID().toString())
                .build();
    }
}
