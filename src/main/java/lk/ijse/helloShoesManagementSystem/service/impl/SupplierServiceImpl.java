package lk.ijse.helloShoesManagementSystem.service.impl;

import lk.ijse.helloShoesManagementSystem.dto.SupplierDTO;
import lk.ijse.helloShoesManagementSystem.entity.SupplierEntity;
import lk.ijse.helloShoesManagementSystem.exception.NotFoundException;
import lk.ijse.helloShoesManagementSystem.repository.SupplierRepo;
import lk.ijse.helloShoesManagementSystem.service.SupplierService;
import lk.ijse.helloShoesManagementSystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepo supplierRepo;
    private final Mapper mapper;

    @Override
    public void saveSupplier(SupplierDTO supplierDTO) {
        supplierDTO.setSupplierId(UUID.randomUUID().toString());
        supplierRepo.save(mapper.toSupplierEntity(supplierDTO));
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return mapper.toSupplierDTOList(supplierRepo.findAll());
    }

    @Override
    public SupplierDTO getSelectedSupplier(String id) {
        if (!supplierRepo.existsById(id)) throw new NotFoundException("Supplier not Found");
        return mapper.toSupplierDTO(supplierRepo.getReferenceById(id));
    }

    @Override
    public void deleteSupplier(String id) {
        if (!supplierRepo.existsById(id)) throw new NotFoundException("Supplier not Found");
        supplierRepo.deleteById(id);
    }

    @Override
    public void updateSupplier(String id, SupplierDTO supplierDTO) {
        Optional<SupplierEntity> supplierEntity = supplierRepo.findById(id);
        if (supplierEntity.isEmpty()) throw new NotFoundException("Supplier not Found");
        supplierEntity.get().setSupplierName(supplierDTO.getSupplierName());
        supplierEntity.get().setSupplierCategory(supplierDTO.getSupplierCategory());
        supplierEntity.get().setAddress(supplierDTO.getAddress());
        supplierEntity.get().setContact1(supplierDTO.getContact1());
        supplierEntity.get().setContact2(supplierDTO.getContact2());
        supplierEntity.get().setEmail(supplierDTO.getEmail());
    }
}
