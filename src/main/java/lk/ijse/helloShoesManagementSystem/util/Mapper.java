package lk.ijse.helloShoesManagementSystem.util;

import lk.ijse.helloShoesManagementSystem.dto.CustomerDTO;
import lk.ijse.helloShoesManagementSystem.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final ModelMapper modelMapper;

    public CustomerEntity toCustomerEntity(CustomerDTO customerDTO){
        return modelMapper.map(customerDTO, CustomerEntity.class);
    }

    public CustomerDTO toCustomerDTO(CustomerEntity customerEntity){
        return modelMapper.map(customerEntity, CustomerDTO.class);
    }

    public List<CustomerDTO> toCustomerDTOList(List<CustomerEntity> customerEntities){
        return modelMapper.map(customerEntities, List.class);
    }

}
