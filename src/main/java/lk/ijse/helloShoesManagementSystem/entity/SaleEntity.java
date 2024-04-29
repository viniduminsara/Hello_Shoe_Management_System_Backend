package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "sale")
public class SaleEntity {

    @Id
    private String orderId;
    private Timestamp date;
    private Double total;
    private Double addedPoints;
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private CustomerEntity customer;

}
