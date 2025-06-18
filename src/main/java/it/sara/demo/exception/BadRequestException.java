package it.sara.demo.exception;

import it.sara.demo.dto.StatusDTO;
import lombok.Getter;

@Getter
public class BadRequestException extends Exception {


    public final static StatusDTO BAD_REQUEST = StatusDTO.builder().code(400).message("Bad Request").build();
    
    private final StatusDTO status;

    public BadRequestException(StatusDTO status) {
        this.status = status;
    }

    public BadRequestException(int code, String message) {
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
