package lk.ijse.helloShoesManagementSystem.service.impl;

import lk.ijse.helloShoesManagementSystem.dto.CustomerDTO;
import lk.ijse.helloShoesManagementSystem.entity.CustomerEntity;
import lk.ijse.helloShoesManagementSystem.exception.NotFoundException;
import lk.ijse.helloShoesManagementSystem.repository.CustomerRepo;
import lk.ijse.helloShoesManagementSystem.service.CustomerService;
import lk.ijse.helloShoesManagementSystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final Mapper mapper;

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        customerDTO.setCustomerId(UUID.randomUUID().toString());
        customerDTO.setJoinedDate(Date.valueOf(LocalDate.now()));
        customerRepo.save(mapper.toCustomerEntity(customerDTO));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return mapper.toCustomerDTOList(customerRepo.findAll());
    }

    @Override
    public CustomerDTO getSelectedCustomer(String id) {
        if (!customerRepo.existsById(id)) throw new NotFoundException("Customer not Found");
        return mapper.toCustomerDTO(customerRepo.getReferenceById(id));
    }

    @Override
    public void deleteCustomer(String id) {
        if (!customerRepo.existsById(id)) throw new NotFoundException("Customer not Found");
        customerRepo.deleteById(id);
    }

    @Override
    public void updateCustomer(String id, CustomerDTO customerDTO) {
        Optional<CustomerEntity> customerEntity = customerRepo.findById(id);
        if (customerEntity.isEmpty()) throw new NotFoundException("Customer Not Found");
        customerEntity.get().setName(customerDTO.getName());
        customerEntity.get().setGender(customerDTO.getGender());
        customerEntity.get().setDob(customerDTO.getDob());
        customerEntity.get().setAddress(customerDTO.getAddress());
        customerEntity.get().setContact(customerDTO.getContact());
        customerEntity.get().setEmail(customerDTO.getEmail());
    }

}
