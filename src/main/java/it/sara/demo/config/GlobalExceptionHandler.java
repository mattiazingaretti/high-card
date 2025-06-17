package it.sara.demo.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import it.sara.demo.dto.StatusDTO;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StatusDTO> handleValidationExcpetions(MethodArgumentNotValidException exception){
        exception.printStackTrace();
        String message = exception.getBindingResult().getFieldErrors().stream().map(field -> field.getDefaultMessage()).reduce((msg1, msg2) ->  msg1 + " && " + msg2).orElse("Invalid input");
        StatusDTO status = StatusDTO.builder()
                .code(400)
                .message(message)
                .traceId(java.util.UUID.randomUUID().toString())
                .build();
        return ResponseEntity.ok().body(status);
    }

    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StatusDTO> handleGenericException(Exception exception) {
        exception.printStackTrace();
        StatusDTO status = StatusDTO.builder()
            .code(500)
            .message("Generic Error")
            .traceId(java.util.UUID.randomUUID().toString())
            .build();
        return ResponseEntity.ok().body(status);
    }

}
