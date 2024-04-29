package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "stock_item")
public class StockItemEntity {

    @Id
    private String stockItemId;
    private Integer qty;
    private Double buyingPrice;
    private Double sellingPrice;

    @ManyToOne
    @JoinColumn(name = "stockId", nullable = false)
    private StockEntity stockEntity;

    @ManyToOne
    @JoinColumn(name = "sizeId", nullable = false)
    private SizeEntity sizeEntity;

    @OneToMany(mappedBy = "stockItemEntity")
    private List<SaleDetailsEntity> saleDetailsEntities;

}
