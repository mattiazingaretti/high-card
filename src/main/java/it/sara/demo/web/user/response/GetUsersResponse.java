package it.sara.demo.web.user.response;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.web.response.GenericPagedResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class GetUsersResponse extends GenericPagedResponse {
    private List<UserDTO> users;
}
