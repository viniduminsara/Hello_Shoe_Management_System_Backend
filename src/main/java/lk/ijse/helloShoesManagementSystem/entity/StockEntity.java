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
@Table(name = "stock")
public class StockEntity {

    @Id
    private String stockId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemCode")
    private ItemEntity itemEntity;

    @OneToMany(mappedBy = "stockEntity", cascade = CascadeType.ALL)
    private List<StockItemEntity> stockItemEntities;

    @ManyToOne
    @JoinColumn(name = "supplierId", nullable = false)
    private SupplierEntity supplierEntity;

}
