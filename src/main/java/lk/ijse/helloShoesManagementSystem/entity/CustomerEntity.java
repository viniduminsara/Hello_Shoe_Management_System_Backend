package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.Gender;
import lk.ijse.helloShoesManagementSystem.entity.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    private String customerId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date joinedDate;
    @Enumerated(EnumType.STRING)
    private Level level;
    private Date dob;
    private String address;
    private Integer totalPoints;
    private Timestamp lastPurchaseDate;
    private String contact;
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<SaleEntity> saleEntities;

}
