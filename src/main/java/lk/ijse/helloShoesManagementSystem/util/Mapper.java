package lk.ijse.helloShoesManagementSystem.util;

import lk.ijse.helloShoesManagementSystem.dto.CustomerDTO;
import lk.ijse.helloShoesManagementSystem.dto.EmployeeDTO;
import lk.ijse.helloShoesManagementSystem.dto.InventoryDTO;
import lk.ijse.helloShoesManagementSystem.entity.CustomerEntity;
import lk.ijse.helloShoesManagementSystem.entity.EmployeeEntity;
import lk.ijse.helloShoesManagementSystem.entity.ItemEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final ModelMapper modelMapper;

    //Customer
    public CustomerEntity toCustomerEntity(CustomerDTO customerDTO){
        return modelMapper.map(customerDTO, CustomerEntity.class);
    }

    public CustomerDTO toCustomerDTO(CustomerEntity customerEntity){
        return modelMapper.map(customerEntity, CustomerDTO.class);
    }

    public List<CustomerDTO> toCustomerDTOList(List<CustomerEntity> customerEntities){
        return modelMapper.map(customerEntities, List.class);
    }

    //Employee
    public EmployeeEntity toEmployeeEntity(EmployeeDTO employeeDTO){
        return modelMapper.map(employeeDTO, EmployeeEntity.class);
    }

    public EmployeeDTO toEmployeeDTO(EmployeeEntity employeeEntity){
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> toEmployeeDTOList(List<EmployeeEntity> employeeEntities){
        return modelMapper.map(employeeEntities, List.class);
    }

    //Item
    public ItemEntity toItemEntity(InventoryDTO inventoryDTO){
        return modelMapper.map(inventoryDTO, ItemEntity.class);
    }

}
