package it.sara.demo.service.database.model;

import it.sara.demo.service.user.model.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String guid;
    private String firstName;
    private String lastName;
    private String email; // mi immagino questa colonna a DB con un bel UNIQUE.
    private String phoneNumber;
    private String password; 
    private UserRole role; //Sarebbe preferibile una tabella dedicata per i ruoli e quindi una conseguente Pojo class
}
