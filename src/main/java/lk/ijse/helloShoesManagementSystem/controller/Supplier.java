package lk.ijse.helloShoesManagementSystem.controller;

import lk.ijse.helloShoesManagementSystem.dto.SupplierDTO;
import lk.ijse.helloShoesManagementSystem.exception.NotFoundException;
import lk.ijse.helloShoesManagementSystem.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/supplier")
@RequiredArgsConstructor
public class Supplier {

    private final SupplierService supplierService;
    private static final Logger logger = LoggerFactory.getLogger(Supplier.class);

    @GetMapping("/health")
    public String supplierHealth(){
        return "OK";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveSupplier(@Validated @RequestBody SupplierDTO supplierDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        logger.info("Received request for save a supplier");
        try {
            supplierService.saveSupplier(supplierDTO);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSuppliers(){
        logger.info("Received request for get All suppliers");
        try {
            return ResponseEntity.ok(supplierService.getAllSuppliers());
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSelectedSupplier(@PathVariable("id") String id){
        logger.info("Received request for get a supplier");
        try {
            return ResponseEntity.ok(supplierService.getSelectedSupplier(id));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSupplier(@PathVariable("id") String id){
        logger.info("Received request for delete a supplier");
        try {
            supplierService.deleteSupplier(id);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSupplier(
            @PathVariable("id") String id,
            @Validated @RequestBody SupplierDTO supplierDTO,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        logger.info("Received request for update a supplier");
        try {
            supplierService.updateSupplier(id, supplierDTO);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
