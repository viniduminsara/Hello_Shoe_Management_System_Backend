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

import java.util.ArrayList;
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
        List<ItemEntity> itemEntities = itemRepo.findAll();
        List<InventoryDTO> inventoryDTOS = new ArrayList<>();

        for (ItemEntity itemEntity : itemEntities) {
            inventoryDTOS.add(convertToInventoryDTO(itemEntity));
        }

        return inventoryDTOS;
    }

    @Override
    public InventoryDTO getSelectedInventory(String id) {
//        return itemRepo.getInventoryDTOByItemCode(id);
        return null;
    }

    private InventoryDTO convertToInventoryDTO(ItemEntity itemEntity){
        InventoryDTO inventoryDTO = new InventoryDTO();

        inventoryDTO.setItemCode(itemEntity.getItemCode());
        inventoryDTO.setItemDesc(itemEntity.getItemDesc());
        inventoryDTO.setItemPic(itemEntity.getItemPic());
        inventoryDTO.setGender(itemEntity.getGender());
        inventoryDTO.setOccasionType(itemEntity.getOccasionType());
        inventoryDTO.setVerityType(itemEntity.getVerityType());

        inventoryDTO.setQty(itemEntity.getItemSizeEntities().get(0).getQty());
        inventoryDTO.setSize(itemEntity.getItemSizeEntities().get(0).getSizeEntity().getSize());
        inventoryDTO.setSupplierId(itemEntity.getSupplierEntity().getSupplierId());
        inventoryDTO.setSupplierName(itemEntity.getSupplierEntity().getSupplierName());

        double selling = itemEntity.getItemSizeEntities().get(0).getSellingPrice();
        double buying = itemEntity.getItemSizeEntities().get(0).getBuyingPrice();

        inventoryDTO.setSellingPrice(itemEntity.getItemSizeEntities().get(0).getSellingPrice());
        inventoryDTO.setBuyingPrice(itemEntity.getItemSizeEntities().get(0).getBuyingPrice());
        inventoryDTO.setProfit(selling - buying);
        inventoryDTO.setProfitMargin((selling - buying)/selling * 100);
        if (itemEntity.getItemSizeEntities().get(0).getQty() > 5){
            inventoryDTO.setStatus("Available");
        }else if (itemEntity.getItemSizeEntities().get(0).getQty() < 5){
            inventoryDTO.setStatus("Low");
        }else {
            inventoryDTO.setStatus("Not Available");
        }

        return inventoryDTO;
    }

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
