package lk.ijse.helloShoesManagementSystem.service;

import lk.ijse.helloShoesManagementSystem.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {

    void saveSupplier(SupplierDTO supplierDTO);

    List<SupplierDTO> getAllSuppliers();

    SupplierDTO getSelectedSupplier(String id);

    void deleteSupplier(String id);

    void updateSupplier(String id, SupplierDTO supplierDTO);

}
