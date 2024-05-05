package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.SupplierCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "supplier")
public class SupplierEntity {

    @Id
    private String supplierId;
    private String supplierName;
    @Enumerated(EnumType.STRING)
    private SupplierCategory supplierCategory;
    private String address;
    private String contact1;
    private String contact2;
    private String email;

    @OneToMany(mappedBy = "supplierEntity", cascade = CascadeType.ALL)
    private List<StockEntity> stockEntities;

}
