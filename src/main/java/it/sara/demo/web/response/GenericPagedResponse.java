package it.sara.demo.web.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GenericPagedResponse extends GenericResponse {
    private int total;
}
