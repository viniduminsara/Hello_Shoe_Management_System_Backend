package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.Gender;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    private String employeeId;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String profilePic;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String civilState;
    private String designation;
    private Date dob;
    private Date joinedDate;
    private String address;
    private String contact;
    private String email;
    private String guardian;
    private String emergencyContact;

}
