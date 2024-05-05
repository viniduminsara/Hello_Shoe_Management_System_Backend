package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.Gender;
import lk.ijse.helloShoesManagementSystem.entity.enums.OccasionType;
import lk.ijse.helloShoesManagementSystem.entity.enums.VerityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    private String itemCode;
    private String itemDesc;
    @Column(columnDefinition = "LONGTEXT")
    private String itemPic;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private OccasionType occasionType;
    @Enumerated(EnumType.STRING)
    private VerityType verityType;

    @OneToMany(mappedBy = "itemEntity", cascade = CascadeType.ALL)
    private List<ItemSizeEntity> itemSizeEntities;

    @ManyToOne
    @JoinColumn(name = "supplierId", nullable = false)
    private SupplierEntity supplierEntity;

}
