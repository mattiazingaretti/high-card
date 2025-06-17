package it.sara.demo.service.user.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public GetUsersResult getUsers(CriteriaGetUsers criteriaGetUsers) throws GenericException {
        return null;
    }
}
