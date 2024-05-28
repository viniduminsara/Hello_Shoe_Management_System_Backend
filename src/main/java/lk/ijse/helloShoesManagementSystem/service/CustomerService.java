package lk.ijse.helloShoesManagementSystem.service;

import lk.ijse.helloShoesManagementSystem.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    void saveCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getSelectedCustomer(String id);

    void deleteCustomer(String id);

    void updateCustomer(String id, CustomerDTO customerDTO);

    CustomerDTO getCustomerByContact(String contact);

}
