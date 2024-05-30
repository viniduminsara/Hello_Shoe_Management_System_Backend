package lk.ijse.helloShoesManagementSystem.service;

import lk.ijse.helloShoesManagementSystem.dto.RefundDTO;
import lk.ijse.helloShoesManagementSystem.dto.SaleDTO;
import lk.ijse.helloShoesManagementSystem.dto.SaleDetailsDTO;

import java.util.List;

public interface SaleService {

    void saveSale(SaleDTO saleDTO);

    List<SaleDTO> getAllSales();

    List<SaleDetailsDTO> getSelectedSale(String id);

    void saveRefund(RefundDTO refundDTO);

}
