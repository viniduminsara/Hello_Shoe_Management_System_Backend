package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    private String employeeId;
    private String name;
    private String profilePic;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String civilState;
    private Date dob;
    private Date joinedDate;
    private String branch;
    private String address;
    private String contact;
    private String email;
    private String guardian;
    private String emergencyContact;

    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity user;
}
