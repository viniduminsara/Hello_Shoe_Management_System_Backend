package lk.ijse.helloShoesManagementSystem.repository;

import lk.ijse.helloShoesManagementSystem.entity.SaleDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDetailsRepo extends JpaRepository<SaleDetailsEntity, String> {
}
