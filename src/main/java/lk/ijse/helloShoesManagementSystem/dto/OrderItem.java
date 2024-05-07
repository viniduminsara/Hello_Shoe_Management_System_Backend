package lk.ijse.helloShoesManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderItem {

    @NotBlank(message = "Item Id cannot be blank")
    private String itemCode;

    @NotBlank(message = "Size Id cannot be blank")
    private String sizeId;

    @NotNull(message = "Unit price cannot be null")
    private Double unitPrice;

    @NotNull(message = "QTY cannot be null")
    private Integer itemQty;

}
