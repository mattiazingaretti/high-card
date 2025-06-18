package it.sara.demo.web.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sara.demo.dto.StatusDTO;
import it.sara.demo.exception.BadRequestException;
import it.sara.demo.exception.GenericException;
import it.sara.demo.service.user.UserService;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.web.user.request.GetUsersRequest;
import it.sara.demo.web.user.response.GetUsersResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/user", headers = "API-Version=1.0.0")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    //With the Authenticaation flow this REST endpoint should not be present (and also a POST would be more appropriate)
    // @PutMapping(value = "/user")
    // public ResponseEntity<AddUserResponse> addUser(@RequestBody @Valid AddUserRequest request) throws GenericException {
    //         CriteriaAddUser criteria = modelMapper.map(request, CriteriaAddUser.class);
    //         AddUserResult result = userService.addUser(criteria);
    //         return ResponseEntity
    //             .status(HttpStatus.OK)
    //             .body(AddUserResponse.builder()
    //                 .result(result)
    //                 .status(StatusDTO.success("User added successfully"))
    //                 .build());    
    // }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<GetUsersResponse> getUsers( @Valid GetUsersRequest request) throws GenericException {
        CriteriaGetUsers criteria = modelMapper.map(request, CriteriaGetUsers.class);
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(GetUsersResponse.builder()
                    .users(userService.getUsers(criteria).getUsers())
                    .status(StatusDTO.success("User Searched successfully"))
                    .build());
    }
}
