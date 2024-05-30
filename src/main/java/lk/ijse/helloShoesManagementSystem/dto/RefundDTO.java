package lk.ijse.helloShoesManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RefundDTO {

    @Null
    private String refundId;

    @Null
    private Timestamp date;

    @NotNull(message = "Amount cannot be null")
    private Double amount;

    @NotNull(message = "Qty cannot be null")
    private Integer qty;

    @NotBlank(message = "SaleDetailsId cannot be blank")
    private String saleDetailsId;

}
