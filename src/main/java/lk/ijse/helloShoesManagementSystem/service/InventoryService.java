package lk.ijse.helloShoesManagementSystem.service;

import lk.ijse.helloShoesManagementSystem.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {

    void saveInventory(InventoryDTO inventoryDTO);

    List<InventoryDTO> getAllInventories();

    InventoryDTO getSelectedInventory(String id);

    void deleteInventory(String id);

    void updateInventory(String id, InventoryDTO inventoryDTO);

    List<InventoryDTO> getSortedInventories(String sortBy);

}
