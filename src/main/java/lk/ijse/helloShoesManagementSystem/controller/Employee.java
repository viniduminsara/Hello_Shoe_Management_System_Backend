package lk.ijse.helloShoesManagementSystem.controller;

import jakarta.validation.Valid;
import lk.ijse.helloShoesManagementSystem.dto.EmployeeDTO;
import lk.ijse.helloShoesManagementSystem.entity.enums.Gender;
import lk.ijse.helloShoesManagementSystem.exception.DuplicateException;
import lk.ijse.helloShoesManagementSystem.exception.NotFoundException;
import lk.ijse.helloShoesManagementSystem.service.EmployeeService;
import lk.ijse.helloShoesManagementSystem.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class Employee {

    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(Employee.class);

    @GetMapping("/health")
    public String employeeHealth(){
        return "OK";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveEmployee(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO,
                                          @RequestPart("profilePicture") MultipartFile profilePicture,
                                          Errors errors) {
        logger.info("Received request for save a employee");
        if (errors.hasFieldErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errors.getFieldErrors().get(0).getDefaultMessage());
        }

        employeeDTO.setProfilePic(UtilMatters.convertBase64(profilePicture));

        try {
            employeeService.saveEmployee(employeeDTO);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DuplicateException e){
            logger.error("Duplicate email error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEmployees(){
        logger.info("Received request for get All employees");
        try {
            return ResponseEntity.ok(employeeService.getAllEmployees());
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSelectedEmployee(@PathVariable("id") String id){
        logger.info("Received request for get a employee");
        try {
            return ResponseEntity.ok(employeeService.getSelectedEmployee(id));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") String id){
        logger.info("Received request for delete a employee");
        try {
            employeeService.deleteEmployee(id);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateEmployee(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO,
                                            @PathVariable String id,
                                            Errors errors){
        logger.info("Received request for update a employee");
        if (errors.hasFieldErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errors.getFieldErrors().get(0).getDefaultMessage());
        }

        try {
            employeeService.updateEmployee(id, employeeDTO);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
