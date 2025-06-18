package it.sara.demo.config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import it.sara.demo.dto.StatusDTO;
import it.sara.demo.exception.BadRequestException;
import it.sara.demo.exception.GenericException;
import it.sara.demo.exception.NotFoundExcpetion;
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

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<StatusDTO> handleGenericException(GenericException exception) {
        exception.printStackTrace();
        StatusDTO status = StatusDTO.builder()
            .code(exception.getStatus().getCode())
            .message(exception.getStatus().getMessage())
            .traceId(java.util.UUID.randomUUID().toString())
            .build();
        return ResponseEntity.ok().body(status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StatusDTO> handleBadRequestException(BadRequestException exception) {
        exception.printStackTrace();
        StatusDTO status = StatusDTO.builder()
            .code(exception.getStatus().getCode())
            .message(exception.getStatus().getMessage())
            .traceId(java.util.UUID.randomUUID().toString())
            .build();
        return ResponseEntity.ok().body(status);
    }

    @ExceptionHandler(NotFoundExcpetion.class)
    public ResponseEntity<StatusDTO> handleNotFoundException(NotFoundExcpetion exception) {
        exception.printStackTrace();
        StatusDTO status = StatusDTO.builder()
            .code(exception.getStatus().getCode())
            .message(exception.getStatus().getMessage())
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StatusDTO> handleBadCredentialsException(BadCredentialsException exception) {
        exception.printStackTrace();
        StatusDTO status = StatusDTO.builder()
            .code(401)
            .message("Bad Credentials")
            .traceId(java.util.UUID.randomUUID().toString())
            .build();
        return ResponseEntity.ok().body(status);
    }
}
