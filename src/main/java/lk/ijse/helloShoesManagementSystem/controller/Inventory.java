package lk.ijse.helloShoesManagementSystem.controller;

import jakarta.validation.Valid;
import lk.ijse.helloShoesManagementSystem.dto.InventoryDTO;
import lk.ijse.helloShoesManagementSystem.entity.enums.Gender;
import lk.ijse.helloShoesManagementSystem.entity.enums.OccasionType;
import lk.ijse.helloShoesManagementSystem.entity.enums.VerityType;
import lk.ijse.helloShoesManagementSystem.exception.NotFoundException;
import lk.ijse.helloShoesManagementSystem.service.InventoryService;
import lk.ijse.helloShoesManagementSystem.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class Inventory {

    private final InventoryService inventoryService;
    private static final Logger logger = LoggerFactory.getLogger(Inventory.class);

    @GetMapping("/health")
    public String itemHealth(){
        return "OK";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveItem(@Valid
                                      @RequestParam String itemDesc,
                                      @RequestPart String itemPic,
                                      @RequestParam Gender gender,
                                      @RequestParam OccasionType occasionType,
                                      @RequestParam VerityType verityType,
                                      @RequestParam Integer qty,
                                      @RequestParam Integer size,
                                      @RequestParam String supplierId,
                                      @RequestParam Double sellingPrice,
                                      @RequestParam Double buyingPrice,
                                      Errors errors){
        logger.info("Received request for save a item");
        if (errors.hasFieldErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errors.getFieldErrors().get(0).getDefaultMessage());
        }

        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setItemDesc(itemDesc);
        inventoryDTO.setItemPic(UtilMatters.convertBase64(itemPic));
        inventoryDTO.setGender(gender);
        inventoryDTO.setOccasionType(occasionType);
        inventoryDTO.setVerityType(verityType);
        inventoryDTO.setQty(qty);
        inventoryDTO.setSize(size);
        inventoryDTO.setSupplierId(supplierId);
        inventoryDTO.setSellingPrice(sellingPrice);
        inventoryDTO.setBuyingPrice(buyingPrice);

        try {
            inventoryService.saveInventory(inventoryDTO);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllInventories(){
        logger.info("Received request for get All inventories");
        try {
            return ResponseEntity.ok(inventoryService.getAllInventories());
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSelectedInventory(@PathVariable("id") String id){
        logger.info("Received request for get a inventory");
        try {
            return null;
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
