package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "refund")
public class RefundEntity {

    @Id
    private String refundId;
    private Timestamp date;
    private Double amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "saleDetailsId")
    private SaleDetailsEntity saleEntity;

}
