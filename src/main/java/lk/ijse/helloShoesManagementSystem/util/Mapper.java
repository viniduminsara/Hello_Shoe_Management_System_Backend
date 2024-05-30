package lk.ijse.helloShoesManagementSystem.util;

import lk.ijse.helloShoesManagementSystem.dto.*;
import lk.ijse.helloShoesManagementSystem.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
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

    //Supplier
    public SupplierEntity toSupplierEntity(SupplierDTO supplierDTO){
        return modelMapper.map(supplierDTO, SupplierEntity.class);
    }

    public SupplierDTO toSupplierDTO(SupplierEntity supplierEntity){
        return modelMapper.map(supplierEntity, SupplierDTO.class);
    }

    public List<SupplierDTO> toSupplierDTOList(List<SupplierEntity> supplierEntities){
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper.map(supplierEntities, List.class);
    }

    //Sale
    public SaleEntity toSaleEntity(SaleDTO saleDTO){
        return modelMapper.map(saleDTO, SaleEntity.class);
    }

    public SaleDTO toSaleDTO(SaleEntity saleEntity){
        return modelMapper.map(saleEntity, SaleDTO.class);
    }

    public List<SaleDTO> toSaleDTOList(List<SaleEntity> saleEntities){
        return modelMapper.map(saleEntities, List.class);
    }

    //User
    public UserEntity toUserEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, UserEntity.class);
    }

    public UserDTO toUserDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    //Refund
    public RefundEntity toRefundEntity(RefundDTO refundDTO){
        return modelMapper.map(refundDTO, RefundEntity.class);
    }
}
