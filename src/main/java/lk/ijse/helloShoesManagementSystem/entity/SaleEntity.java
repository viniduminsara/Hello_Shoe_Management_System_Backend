package lk.ijse.helloShoesManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.PaymentMethod;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "sale")
public class SaleEntity {

    @Id
    private String orderId;
    private Timestamp date;
    private Double total;
    private Double addedPoints;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customerId", nullable = false)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "saleEntity")
    @JsonManagedReference
    private List<SaleDetailsEntity> saleDetailsEntities;

}
