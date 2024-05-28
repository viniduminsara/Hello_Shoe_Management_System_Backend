package lk.ijse.helloShoesManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lk.ijse.helloShoesManagementSystem.entity.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SaleDTO {

    @Null
    private String orderId;

    private String customerId;

    @NotNull(message = "Total Price cannot be null")
    private Double totalPrice;

    @NotNull(message = "Purchase date cannot be null")
    private Timestamp purchaseDate;

    @NotNull(message = "Payment Method cannot be null")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Added points cannot be null")
    private Double addedPoints;

    @NotBlank(message = "Employee Id cannot be blank")
    private String userId;

    @NotNull(message = "Order Items cannot be null")
    private List<OrderItem> orderItems;

}
