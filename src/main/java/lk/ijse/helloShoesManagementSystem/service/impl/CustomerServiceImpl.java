package lk.ijse.helloShoesManagementSystem.service.impl;

import lk.ijse.helloShoesManagementSystem.dto.CustomerDTO;
import lk.ijse.helloShoesManagementSystem.entity.CustomerEntity;
import lk.ijse.helloShoesManagementSystem.entity.enums.Level;
import lk.ijse.helloShoesManagementSystem.exception.DuplicateException;
import lk.ijse.helloShoesManagementSystem.exception.NotFoundException;
import lk.ijse.helloShoesManagementSystem.repository.CustomerRepo;
import lk.ijse.helloShoesManagementSystem.service.CustomerService;
import lk.ijse.helloShoesManagementSystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
        if (customerRepo.findByContact(customerDTO.getContact()).isPresent()){
            throw new DuplicateException("A customer with this contact number already exists");
        }
        customerDTO.setCustomerId(UUID.randomUUID().toString());
        customerDTO.setJoinedDate(Date.valueOf(LocalDate.now()));
        customerRepo.save(mapper.toCustomerEntity(customerDTO));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return convertToCustomerDTOList(customerRepo.findAll());
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

    @Override
    public CustomerDTO getCustomerByContact(String contact) {
        return mapper.toCustomerDTO(customerRepo.findByContact(contact)
                .orElseThrow(() -> new NotFoundException("Customer not Found")));
    }

    private List<CustomerDTO> convertToCustomerDTOList(List<CustomerEntity> customerEntities) {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (CustomerEntity customerEntity : customerEntities) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerId(customerEntity.getCustomerId());
            customerDTO.setAddress(customerEntity.getAddress());
            customerDTO.setContact(customerEntity.getContact());
            customerDTO.setDob(customerEntity.getDob());
            customerDTO.setEmail(customerEntity.getEmail());
            customerDTO.setGender(customerEntity.getGender());
            customerDTO.setJoinedDate(customerEntity.getJoinedDate());
            customerDTO.setName(customerEntity.getName());

            Double totalPoints = customerRepo.findTotalPointsByCustomerId(customerEntity.getCustomerId());
            if (totalPoints == null) {
                totalPoints = 0.0;
            }
            customerDTO.setTotalPoints(totalPoints);
            customerDTO.setLevel(getCustomerLevel(totalPoints));
            customerDTO.setLastSaleDate(customerRepo.findLastSaleDateByCustomerId(customerEntity.getCustomerId()));
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public Level getCustomerLevel(Double totalPoints) {
        if (totalPoints > 200) {
            return Level.GOLD;
        } else if (totalPoints >= 100) {
            return Level.SILVER;
        } else if (totalPoints >= 50) {
            return Level.BRONZE;
        } else {
            return Level.NEW;
        }
    }

}
