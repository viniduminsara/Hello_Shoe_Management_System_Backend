package lk.ijse.helloShoesManagementSystem.service.impl;

import lk.ijse.helloShoesManagementSystem.dto.InventoryDTO;
import lk.ijse.helloShoesManagementSystem.entity.ItemEntity;
import lk.ijse.helloShoesManagementSystem.entity.ItemSizeEntity;
import lk.ijse.helloShoesManagementSystem.entity.SizeEntity;
import lk.ijse.helloShoesManagementSystem.entity.SupplierEntity;
import lk.ijse.helloShoesManagementSystem.entity.enums.Gender;
import lk.ijse.helloShoesManagementSystem.exception.NotFoundException;
import lk.ijse.helloShoesManagementSystem.repository.*;
import lk.ijse.helloShoesManagementSystem.service.InventoryService;
import lk.ijse.helloShoesManagementSystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ItemRepo itemRepo;
    private final SupplierRepo supplierRepo;
    private final SizeRepo sizeRepo;
    private final ItemSizeRepo itemSizeRepo;
    private final Mapper mapper;

    @Override
    public void saveInventory(InventoryDTO inventoryDTO) {
        //item save
        inventoryDTO.setItemCode(generateItemCode(inventoryDTO));
        ItemEntity itemEntity = mapper.toItemEntity(inventoryDTO);
        SupplierEntity supplierEntity = supplierRepo.findById(inventoryDTO.getSupplierId())
                .orElseThrow(() -> new NotFoundException("Supplier not found"));
        itemEntity.setSupplierEntity(supplierEntity);
        itemRepo.save(itemEntity);

        //item size save
        ItemSizeEntity itemSizeEntity = new ItemSizeEntity();
        itemSizeEntity.setItemSizeId(UUID.randomUUID().toString());
        itemSizeEntity.setQty(inventoryDTO.getQty());
        itemSizeEntity.setBuyingPrice(inventoryDTO.getBuyingPrice());
        itemSizeEntity.setSellingPrice(inventoryDTO.getSellingPrice());
        itemSizeEntity.setItemEntity(itemEntity);
        SizeEntity sizeEntity = sizeRepo.findBySize(inventoryDTO.getSize())
                .orElseThrow(() -> new NotFoundException("Size not found"));
        itemSizeEntity.setSizeEntity(sizeEntity);
        itemSizeRepo.save(itemSizeEntity);
    }

    @Override
    public List<InventoryDTO> getAllInventories() {
//        List<StockItemEntity> stockItemEntities = stockItemRepo.findAll();
//        List<InventoryDTO> inventoryDTOs = new ArrayList<>();
//
//        for (StockItemEntity stockItemEntity : stockItemEntities) {
//            inventoryDTOs.add(convertToInventoryDTO(stockItemEntity));
//        }

        return null;
    }

    @Override
    public InventoryDTO getSelectedInventory(String id) {
//        itemRepo.getReferenceById(id)
        return null;
    }

//    private InventoryDTO convertToInventoryDTO(StockItemEntity stockItemEntity){
//        InventoryDTO inventoryDTO = new InventoryDTO();
//        // Set the existing fields
//        inventoryDTO.setItemCode(stockItemEntity.getStockEntity().getItemEntity().getItemCode());
//        inventoryDTO.setItemDesc(stockItemEntity.getStockEntity().getItemEntity().getItemDesc());
//        inventoryDTO.setItemPic(stockItemEntity.getStockEntity().getItemEntity().getItemPic());
//        inventoryDTO.setGender(stockItemEntity.getStockEntity().getItemEntity().getGender());
//        inventoryDTO.setOccasionType(stockItemEntity.getStockEntity().getItemEntity().getOccasionType());
//        inventoryDTO.setVerityType(stockItemEntity.getStockEntity().getItemEntity().getVerityType());
//        inventoryDTO.setQty(stockItemEntity.getQty());
//        inventoryDTO.setSize(stockItemEntity.getSizeEntity().getSize());
//        inventoryDTO.setSupplierId(stockItemEntity.getStockEntity().getSupplierEntity().getSupplierId());
//        inventoryDTO.setSupplierName(stockItemEntity.getStockEntity().getSupplierEntity().getSupplierName());
//        inventoryDTO.setSellingPrice(stockItemEntity.getSellingPrice());
//        inventoryDTO.setBuyingPrice(stockItemEntity.getBuyingPrice());
//
//        // Calculate profit and profit margin
//        double profit = stockItemEntity.getSellingPrice() - stockItemEntity.getBuyingPrice();
//        inventoryDTO.setProfit(profit);
//        double profitMargin = (profit / stockItemEntity.getSellingPrice()) * 100;
//        inventoryDTO.setProfitMargin(profitMargin);
//
//        // Calculate status
//        if (stockItemEntity.getQty() < 5) {
//            inventoryDTO.setStatus("low");
//        } else if (stockItemEntity.getQty() > 5) {
//            inventoryDTO.setStatus("available");
//        } else {
//            inventoryDTO.setStatus("not available");
//        }
//
//        return inventoryDTO;
//    }

    private String generateItemCode(InventoryDTO inventoryDTO){
        StringBuilder itemCodeBuilder = new StringBuilder();

        // Append prefix based on occasion type
        switch (inventoryDTO.getOccasionType()) {
            case FORMAL:
                itemCodeBuilder.append("F");
                break;
            case CASUAL:
                itemCodeBuilder.append("C");
                break;
            case INDUSTRIAL:
                itemCodeBuilder.append("I");
                break;
            case SPORT:
                itemCodeBuilder.append("S");
                break;
        }

        // Append prefix based on verity type
        switch (inventoryDTO.getVerityType()) {
            case HEEL:
                itemCodeBuilder.append("H");
                break;
            case FLATS:
                itemCodeBuilder.append("F");
                break;
            case WEDGES:
                itemCodeBuilder.append("W");
                break;
            case FLIP_FLOPS:
                itemCodeBuilder.append("FF");
                break;
            case SANDALS:
                itemCodeBuilder.append("SD");
                break;
            case SHOES:
                itemCodeBuilder.append("S");
                break;
            case SLIPPERS:
                itemCodeBuilder.append("SL");
                break;
            case SHOE_SHAMPOO:
                itemCodeBuilder.append("SHMP");
                break;
            case POLISH:
                itemCodeBuilder.append("P");
                break;
        }

        if (inventoryDTO.getGender() == Gender.MALE) {
            itemCodeBuilder.append("M");
        } else if (inventoryDTO.getGender() == Gender.FEMALE) {
            itemCodeBuilder.append("W");
        }
        String prefix = itemCodeBuilder.toString();
        String suffix = itemRepo.findLastItemCodeWithPrefix(prefix);

        if (suffix != null){
            return String.format("%s%05d", prefix, Integer.parseInt(suffix.replace(prefix, "")) + 1);
        }
        return prefix + "00001";
    }

}
