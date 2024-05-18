package lk.ijse.helloShoesManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.Gender;
import lk.ijse.helloShoesManagementSystem.entity.enums.OccasionType;
import lk.ijse.helloShoesManagementSystem.entity.enums.VerityType;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

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
    private Double buyingPrice;
    private Double sellingPrice;

    @OneToMany(mappedBy = "itemEntity", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemSizeEntity> itemSizeEntities;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "supplierId", nullable = false)
    private SupplierEntity supplierEntity;

}
