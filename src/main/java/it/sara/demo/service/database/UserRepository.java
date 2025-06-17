package it.sara.demo.service.database;

import it.sara.demo.exception.GenericException;
import it.sara.demo.service.database.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepository {

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
}
