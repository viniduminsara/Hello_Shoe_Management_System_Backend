package lk.ijse.helloShoesManagementSystem.dto;

import jakarta.validation.constraints.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.SupplierCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SupplierDTO {

    @Null
    private String supplierId;

    @NotBlank(message = "Customer name cannot be blank")
    private String supplierName;

    @NotNull(message = "Category cannot be null")
    private SupplierCategory supplierCategory;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "contact 1 cannot be blank")
    @Pattern(regexp = "^\\+?[0-9\\-\\s]+$", message = "Invalid contact number format")
    private String contact1;

    @NotBlank(message = "contact 2 cannot be blank")
    @Pattern(regexp = "^\\+?[0-9\\-\\s]+$", message = "Invalid contact number format")
    private String contact2;

    @Email
    @NotBlank(message = "email cannot be blank")
    private String email;

}
