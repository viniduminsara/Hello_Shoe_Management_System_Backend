package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "item_size")
public class ItemSizeEntity {

    @Id
    private String itemSizeId;
    private Integer qty;
    private Double buyingPrice;
    private Double sellingPrice;

    @ManyToOne
    @JoinColumn(name = "itemCode", nullable = false)
    private ItemEntity itemEntity;

    @ManyToOne
    @JoinColumn(name = "sizeId", nullable = false)
    private SizeEntity sizeEntity;

    @OneToMany(mappedBy = "itemSizeEntity")
    private List<SaleDetailsEntity> saleDetailsEntities;
}
