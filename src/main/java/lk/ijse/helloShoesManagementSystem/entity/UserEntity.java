package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    private String userId;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId")
    private EmployeeEntity employeeEntity;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SaleEntity> saleEntities;

}
