package lk.ijse.helloShoesManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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

public class InventoryDTO {

    @Null
    private String itemCode;

    @NotBlank(message = "item description cannot be blank")
    private String itemDesc;

    @Null
    private String itemPic;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotNull(message = "Occasion type cannot be null")
    private OccasionType occasionType;

    @NotNull(message = "Verity type cannot be null")
    private VerityType verityType;

    @NotNull(message = "item sizes cannot be null")
    private List<ItemSizeDTO> itemSizeDTOS;

//    @NotNull(message = "Quantity cannot be null")
//    private Integer qty;
//
//    @NotNull(message = "Size cannot be null")
//    private Integer size;

    @NotBlank(message = "Item picture cannot be blank")
    private String supplierId;

    @Null
    private String supplierName;

    @NotNull(message = "SellingPrice cannot be null")
    private Double sellingPrice;

    @NotNull(message = "BuyingPrice cannot be null")
    private Double buyingPrice;

    @Null
    private Double profit;

    @Null
    private Double profitMargin;

    @Null
    private String status;

}
