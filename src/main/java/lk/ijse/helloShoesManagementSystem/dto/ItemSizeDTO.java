package lk.ijse.helloShoesManagementSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ItemSizeDTO {

    @NotNull(message = "Size cannot be null")
    private Integer size;

    @NotNull(message = "Quantity cannot be null")
    private Integer qty;

}
