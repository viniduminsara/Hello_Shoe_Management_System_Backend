package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Embeddable
public class SaleDetailsKey implements Serializable {

    @Column(name = "orderId")
    private String orderId;

    @Column(name = "itemSizeId")
    private String itemSizeId;

}
