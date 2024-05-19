package lk.ijse.helloShoesManagementSystem.service.impl;

import lk.ijse.helloShoesManagementSystem.dto.InventoryDTO;
import lk.ijse.helloShoesManagementSystem.dto.ItemSizeDTO;
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

import java.util.*;
import java.util.stream.Collectors;

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
        for (ItemSizeDTO itemSizeDTO : inventoryDTO.getItemSizeDTOS()) {
            ItemSizeEntity itemSizeEntity = new ItemSizeEntity();
            itemSizeEntity.setItemSizeId(UUID.randomUUID().toString());
            itemSizeEntity.setQty(itemSizeDTO.getQty());
            itemSizeEntity.setItemEntity(itemEntity);
            SizeEntity sizeEntity = sizeRepo.findBySize(itemSizeDTO.getSize())
                    .orElseThrow(() -> new NotFoundException("Size not found"));
            itemSizeEntity.setSizeEntity(sizeEntity);
            itemSizeRepo.save(itemSizeEntity);
        }
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
        if (!itemRepo.existsById(id)) throw new NotFoundException("Inventory not Found");
        return convertToInventoryDTO(itemRepo.getReferenceById(id));
    }

    @Override
    public void deleteInventory(String id) {
        if (!itemRepo.existsById(id)) throw new NotFoundException("Inventory not Found");
        itemRepo.deleteById(id);
    }

    @Override
    public void updateInventory(String id, InventoryDTO inventoryDTO) {
        Optional<ItemEntity> itemEntityOptional = itemRepo.findById(id);
        if (itemEntityOptional.isEmpty()) throw new NotFoundException("Inventory Not Found");

        ItemEntity itemEntity = itemEntityOptional.get();

        // Update the basic fields of ItemEntity
        itemEntity.setItemDesc(inventoryDTO.getItemDesc());
        itemEntity.setGender(inventoryDTO.getGender());
        itemEntity.setOccasionType(inventoryDTO.getOccasionType());
        itemEntity.setVerityType(inventoryDTO.getVerityType());

        // Create a map of size to ItemSizeEntity for easy lookup
        Map<Integer, ItemSizeEntity> existingSizeMap = itemEntity.getItemSizeEntities().stream()
                .collect(Collectors.toMap(itemSize -> itemSize.getSizeEntity().getSize(), itemSize -> itemSize));

        // Iterate over the incoming DTOs and update existing or add new records
        for (ItemSizeDTO dto : inventoryDTO.getItemSizeDTOS()) {
            if (existingSizeMap.containsKey(dto.getSize())) {
                // Update existing record
                ItemSizeEntity existingItemSize = existingSizeMap.get(dto.getSize());
                existingItemSize.setQty(dto.getQty());
            }
        }

        // Remove records that are no longer in the incoming DTOs
        List<ItemSizeEntity> toRemove = itemEntity.getItemSizeEntities().stream()
                .filter(itemSize -> inventoryDTO.getItemSizeDTOS().stream()
                        .noneMatch(dto -> dto.getSize().equals(itemSize.getSizeEntity().getSize())))
                .collect(Collectors.toList());

        itemEntity.getItemSizeEntities().removeAll(toRemove);

        // Save the updated ItemEntity
        itemRepo.save(itemEntity);
    }



    private InventoryDTO convertToInventoryDTO(ItemEntity itemEntity){
        InventoryDTO inventoryDTO = new InventoryDTO();

        inventoryDTO.setItemCode(itemEntity.getItemCode());
        inventoryDTO.setItemDesc(itemEntity.getItemDesc());
        inventoryDTO.setItemPic(itemEntity.getItemPic());
        inventoryDTO.setGender(itemEntity.getGender());
        inventoryDTO.setOccasionType(itemEntity.getOccasionType());
        inventoryDTO.setVerityType(itemEntity.getVerityType());
        inventoryDTO.setSellingPrice(itemEntity.getSellingPrice());
        inventoryDTO.setBuyingPrice(itemEntity.getBuyingPrice());

        int totalQty = 0;
        List<ItemSizeDTO> itemSizeDTOS = new ArrayList<>();
        for (ItemSizeEntity itemSizeEntity : itemEntity.getItemSizeEntities()) {
            ItemSizeDTO itemSizeDTO = new ItemSizeDTO();
            itemSizeDTO.setSize(itemSizeEntity.getSizeEntity().getSize());
            itemSizeDTO.setQty(itemSizeEntity.getQty());
            totalQty += itemSizeEntity.getQty();

            itemSizeDTOS.add(itemSizeDTO);
        }

        inventoryDTO.setItemSizeDTOS(itemSizeDTOS);
        inventoryDTO.setSupplierId(itemEntity.getSupplierEntity().getSupplierId());
        inventoryDTO.setSupplierName(itemEntity.getSupplierEntity().getSupplierName());
        inventoryDTO.setProfit(itemEntity.getSellingPrice() - itemEntity.getBuyingPrice());
        inventoryDTO.setProfitMargin((itemEntity.getSellingPrice() - itemEntity.getBuyingPrice())/itemEntity.getSellingPrice() * 100);
        if ((totalQty / 7) > 10){
            inventoryDTO.setStatus("Available");
        }else if (totalQty == 0){
            inventoryDTO.setStatus("Not Available");
        } else if ((totalQty / 7) <= 5){
            inventoryDTO.setStatus("Low");
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
