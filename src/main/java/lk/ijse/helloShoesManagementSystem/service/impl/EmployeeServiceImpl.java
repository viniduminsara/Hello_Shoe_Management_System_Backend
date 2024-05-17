package lk.ijse.helloShoesManagementSystem.service.impl;

import lk.ijse.helloShoesManagementSystem.dto.EmployeeDTO;
import lk.ijse.helloShoesManagementSystem.entity.EmployeeEntity;
import lk.ijse.helloShoesManagementSystem.exception.NotFoundException;
import lk.ijse.helloShoesManagementSystem.repository.EmployeeRepo;
import lk.ijse.helloShoesManagementSystem.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final Mapper mapper;

    @Override
    public void saveEmployee(EmployeeDTO employeeDTO) {
        employeeDTO.setEmployeeId(UUID.randomUUID().toString());
        employeeRepo.save(mapper.toEmployeeEntity(employeeDTO));
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return mapper.toEmployeeDTOList(employeeRepo.findAll());
    }

    @Override
    public EmployeeDTO getSelectedEmployee(String id) {
        if (!employeeRepo.existsById(id)) throw new NotFoundException("Employee not found");
        return mapper.toEmployeeDTO(employeeRepo.getReferenceById(id));
    }

    @Override
    public void deleteEmployee(String id) {
        if (!employeeRepo.existsById(id)) throw new NotFoundException("Employee not found");
        employeeRepo.deleteById(id);
    }

    @Override
    public void updateEmployee(String id, EmployeeDTO employeeDTO) {
        Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(id);
        if (employeeEntity.isEmpty()) throw new NotFoundException("Employee not found");
        employeeEntity.get().setName(employeeDTO.getName());
//        employeeEntity.get().setProfilePic(employeeDTO.getProfilePic());
        employeeEntity.get().setGender(employeeDTO.getGender());
        employeeEntity.get().setCivilState(employeeDTO.getCivilState());
        employeeEntity.get().setDesignation(employeeDTO.getDesignation());
        employeeEntity.get().setDob(employeeDTO.getDob());
        employeeEntity.get().setJoinedDate(employeeDTO.getJoinedDate());
        employeeEntity.get().setAddress(employeeDTO.getAddress());
        employeeEntity.get().setContact(employeeDTO.getContact());
        employeeEntity.get().setEmail(employeeDTO.getEmail());
        employeeEntity.get().setGuardian(employeeDTO.getGuardian());
        employeeEntity.get().setEmergencyContact(employeeDTO.getEmergencyContact());
    }
}
