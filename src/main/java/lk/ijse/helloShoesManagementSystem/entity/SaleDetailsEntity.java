package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "sale_details")
public class SaleDetailsEntity {

    @EmbeddedId
    private SaleDetailsKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orderId")
    private SaleEntity saleEntity;

    @ManyToOne
    @MapsId("stockItemId")
    @JoinColumn(name = "stockItemId")
    private StockItemEntity stockItemEntity;

    private Integer qty;

}
