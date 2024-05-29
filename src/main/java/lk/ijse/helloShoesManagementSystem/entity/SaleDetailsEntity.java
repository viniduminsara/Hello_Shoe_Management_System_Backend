package lk.ijse.helloShoesManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "sale_details")
public class SaleDetailsEntity {

//    @EmbeddedId
//    private SaleDetailsKey id;
    @Id
    private String saleDetailsId;

    @ManyToOne
//    @MapsId("orderId")
    @JoinColumn(name = "orderId")
    @JsonBackReference
    private SaleEntity saleEntity;

    @ManyToOne
//    @MapsId("itemSizeId")
    @JoinColumn(name = "itemSizeId")
    @JsonBackReference
    private ItemSizeEntity itemSizeEntity;

    private Integer qty;

    private Double unitPrice;

}
