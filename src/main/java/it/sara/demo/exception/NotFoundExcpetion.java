package it.sara.demo.exception;

import it.sara.demo.dto.StatusDTO;
import lombok.Getter;

@Getter
public class NotFoundExcpetion extends Exception{

    public final static StatusDTO NOT_FOUND_ERROR = StatusDTO.builder().code(400).message("Not Found").build();
    public final static StatusDTO USER_NOT_FOUND_ERROR = StatusDTO.builder().code(400001).message("User Not Found").build();
    
    
    private final StatusDTO status;

    public NotFoundExcpetion(StatusDTO status) {
        this.status = status;
    }

    public NotFoundExcpetion(int code, String message) {
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
