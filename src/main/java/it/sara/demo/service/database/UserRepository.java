package it.sara.demo.service.database;

import it.sara.demo.exception.GenericException;
import it.sara.demo.service.database.model.User;
import it.sara.demo.service.user.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepository {

    private final ModelMapper modelMapper;

    UserRepository(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    /**
     * per prevenire SQL injection in questa repository occorre:
     *  - validare i dati in ingresso allo User
     *  - sanificare tutti gli String fields controllando non ci siano caratteri speciali non ammessi es punti e virgola e parentesi
     *  - chiamare query parametrizzate (o anche indicizzate con ?1 , ?2 ... per i vari argomenti ) preferibilmente su paradigma ORM, ex JPA o Hibernate. 
     * 
     * @param user
     * @return
     * @throws GenericException
     */
    public String save(User user) throws GenericException {
        try{
            user.setGuid(java.util.UUID.randomUUID().toString());
            FakeDatabase.TABLE_USER.add(user);
            return user.getGuid();
        }catch (Exception e) {
            throw new GenericException(500, "Error saving user");
        }
    }

    
    public Optional<User> getByGuid(String guid) {
        return FakeDatabase.TABLE_USER.stream().filter(u -> u.getGuid().equals(guid)).findFirst();
    }

    public List<User> getAll() {
        return FakeDatabase.TABLE_USER;
    }

    public Optional<UserModel> getByEmail(String email) {
        return FakeDatabase.TABLE_USER.stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst().map(u -> modelMapper.map(u, UserModel.class));
    }
}
