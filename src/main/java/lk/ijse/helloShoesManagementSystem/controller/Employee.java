package lk.ijse.helloShoesManagementSystem.controller;

import jakarta.validation.Valid;
import lk.ijse.helloShoesManagementSystem.dto.EmployeeDTO;
import lk.ijse.helloShoesManagementSystem.entity.enums.Gender;
import lk.ijse.helloShoesManagementSystem.service.EmployeeService;
import lk.ijse.helloShoesManagementSystem.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class Employee {

    private final EmployeeService employeeService;

    @GetMapping("/health")
    public String employeeHealth(){
        return "OK";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveEmployee(@Valid
                                          @RequestParam String name,
                                          @RequestPart String profilePic,
                                          @RequestParam Gender gender,
                                          @RequestParam String civilState,
                                          @RequestParam String designation,
                                          @RequestParam Date dob,
                                          @RequestParam Date joinedDate,
                                          @RequestParam String address,
                                          @RequestParam String contact,
                                          @RequestParam String email,
                                          @RequestParam String guardian,
                                          @RequestParam String emergencyContact,
                                          Errors errors){
        if (errors.hasFieldErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errors.getFieldErrors().get(0).getDefaultMessage());
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(name);
        employeeDTO.setProfilePic(UtilMatters.convertBase64(profilePic));
        employeeDTO.setGender(gender);
        employeeDTO.setCivilState(civilState);
        employeeDTO.setDesignation(designation);
        employeeDTO.setDob(dob);
        employeeDTO.setJoinedDate(joinedDate);
        employeeDTO.setAddress(address);
        employeeDTO.setContact(contact);
        employeeDTO.setEmail(email);
        employeeDTO.setGuardian(guardian);
        employeeDTO.setEmergencyContact(emergencyContact);

        try {
            employeeService.saveEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
