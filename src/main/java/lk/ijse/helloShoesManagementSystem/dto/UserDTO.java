package lk.ijse.helloShoesManagementSystem.dto;

import lk.ijse.helloShoesManagementSystem.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDTO {

    private String userId;
    private String email;
    private String password;
    private Role role;

}
