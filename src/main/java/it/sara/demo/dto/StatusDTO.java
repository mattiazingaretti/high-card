package it.sara.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@ToString 
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@Getter
public class StatusDTO {
    private int code;
    private String message;
    private String traceId;

    public static StatusDTO success(String message) {
        return new StatusDTO(200, message != null ? message : "Success", java.util.UUID.randomUUID().toString());
    }
}
