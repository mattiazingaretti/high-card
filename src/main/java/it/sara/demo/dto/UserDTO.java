package it.sara.demo.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@ToString 
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class UserDTO {
    private String guid;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
