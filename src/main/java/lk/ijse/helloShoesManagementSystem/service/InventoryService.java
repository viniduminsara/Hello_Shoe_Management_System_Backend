package lk.ijse.helloShoesManagementSystem.service;

import lk.ijse.helloShoesManagementSystem.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {

    void saveInventory(InventoryDTO inventoryDTO);

    List<InventoryDTO> getAllInventories();

    InventoryDTO getSelectedInventory(String id);

}
