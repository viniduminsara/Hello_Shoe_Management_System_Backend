package lk.ijse.helloShoesManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SaleDetailsDTO {

    @NotBlank(message = "Item Id cannot be blank")
    private String itemCode;

    @NotBlank(message = "item description cannot be blank")
    private String itemDesc;

    @NotBlank(message = "Item Pic cannot be blank")
    private String itemPic;

    @NotBlank(message = "Item Id cannot be blank")
    private String saleDetailsId;

    @NotBlank(message = "Size Id cannot be blank")
    private Integer size;

    @NotNull(message = "Unit price cannot be null")
    private Double unitPrice;

    @NotNull(message = "QTY cannot be null")
    private Integer qty;

}
