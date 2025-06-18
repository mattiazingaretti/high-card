package it.sara.demo.service.user.impl;

import java.util.Comparator;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.exception.GenericException;
import it.sara.demo.service.database.UserRepository;
import it.sara.demo.service.database.model.User;
import it.sara.demo.service.user.UserService;
import it.sara.demo.service.user.criteria.CriteriaAddUser;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.service.user.result.AddUserResult;
import it.sara.demo.service.user.result.GetUsersResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    // With a real Data source configured this would be better to be anntoated with
    // @Transactional(rollbackFor = GenericException.class)
    public AddUserResult addUser(CriteriaAddUser criteria) throws GenericException {
        try {

            AddUserResult returnValue = new AddUserResult();
            User user = modelMapper.map(criteria, User.class);

            String guid = userRepository.save(user);
            returnValue.setGuid(guid);

            return returnValue;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GenericException(GenericException.USER_SAVE_ERROR);
        }

    }

    /**
     * This method retrieves all users from the repository.
     * 
     * @return A list of all users.
     */
    private List<User> getAllUsers() {
        return userRepository.getAll();
    }


    /**
     * This method retrieves all users based on the provided criteria.
     * 
     * @param criteriaGetUsers The criteria for filtering users.
     * @return A result containing the list of users.
     * @throws GenericException If an error occurs while retrieving users.
     */
    @Override
    public GetUsersResult getUsers(CriteriaGetUsers criteria) throws GenericException {
        GetUsersResult result = new GetUsersResult();


        List<User> allUsers = getAllUsers(); 

        String query = criteria.getQuery();
        List<User> filtered = allUsers;

        
        String q = query.toLowerCase();
        filtered = filtered.stream()
            .filter(u -> (u.getFirstName() != null && u.getFirstName().toLowerCase().contains(q)) ||
                        (u.getLastName() != null && u.getLastName().toLowerCase().contains(q)) ||
                        (u.getEmail() != null && u.getEmail().toLowerCase().contains(q)))
            .toList();
    

        Comparator<User> comparator = Comparator.comparing(User::getFirstName, Comparator.nullsLast(String::compareToIgnoreCase));
        switch (criteria.getOrder()) {
            case BY_FIRSTNAME_DESC -> comparator = Comparator.comparing(User::getFirstName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)).reversed();
            case BY_LASTNAME -> comparator = Comparator.comparing(User::getLastName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
            case BY_LASTNAME_DESC -> comparator = Comparator.comparing(User::getLastName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)).reversed();
            default -> comparator = Comparator.comparing(User::getFirstName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
        }
        
        filtered = filtered.stream().sorted(comparator).toList();

        int offset = Math.max(0, criteria.getOffset());
        int limit = Math.max(1, criteria.getLimit());
        int total = filtered.size();

        //Gestisce anche il caso total = 0 in cui non ha senso fare la query a db paginata (ovvero la skippatura nella nostra impl semplificata)
        if (offset >= total) {
            result.setUsers(List.of()); 
            result.setTotal(total);
            return result;
        }
        
        List<User> paged = filtered.stream()
            .skip(offset)
            .limit(limit)
            .toList();

        List<UserDTO> userDTOs = paged.stream()
            .map(user -> modelMapper.map(user, UserDTO.class))
            .toList();
        
        result.setUsers(userDTOs);
        result.setTotal(total);

        return result;
    }
}
