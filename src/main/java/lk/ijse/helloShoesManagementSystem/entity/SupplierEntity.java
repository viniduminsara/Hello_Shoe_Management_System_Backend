package lk.ijse.helloShoesManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.SupplierCategory;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

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
    @JsonManagedReference
    private List<ItemEntity> itemEntities;

}
