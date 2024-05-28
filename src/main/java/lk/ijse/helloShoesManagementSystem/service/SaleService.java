package lk.ijse.helloShoesManagementSystem.service;

import lk.ijse.helloShoesManagementSystem.dto.SaleDTO;

import java.util.List;

public interface SaleService {

    void saveSale(SaleDTO saleDTO);

    List<SaleDTO> getAllSales();

}
