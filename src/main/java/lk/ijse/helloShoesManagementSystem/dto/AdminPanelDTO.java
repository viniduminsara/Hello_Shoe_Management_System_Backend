package lk.ijse.helloShoesManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminPanelDTO {

    private Double totalSales;
    private Double totalProfit;
    private String mostSaleItem;
    private String mostSaleItemPic;
//    private String mostSaleItemQty;

}
