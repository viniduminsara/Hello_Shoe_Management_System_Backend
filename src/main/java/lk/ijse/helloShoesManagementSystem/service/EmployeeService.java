package lk.ijse.helloShoesManagementSystem.service;

import lk.ijse.helloShoesManagementSystem.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    void saveEmployee(EmployeeDTO employeeDTO);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getSelectedEmployee(String id);

    void deleteEmployee(String id);

    void updateEmployee(String id, EmployeeDTO employeeDTO);

}
