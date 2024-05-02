package lk.ijse.helloShoesManagementSystem.dto;

import lk.ijse.helloShoesManagementSystem.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerDTO {

    private String customerId;
    private String name;
    private Gender gender;
    private Date joinedDate;
    private Date dob;
    private String address;
    private String contact;
    private String email;

}
