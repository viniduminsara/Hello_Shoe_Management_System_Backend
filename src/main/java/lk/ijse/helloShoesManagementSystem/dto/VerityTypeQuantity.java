package lk.ijse.helloShoesManagementSystem.dto;

import lk.ijse.helloShoesManagementSystem.entity.enums.VerityType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VerityTypeQuantity {
    private VerityType verityType;
    private Integer quantity;

    public VerityTypeQuantity(VerityType verityType, double quantity) {
        this.verityType = verityType;
        this.quantity = (int) quantity;
    }
}
