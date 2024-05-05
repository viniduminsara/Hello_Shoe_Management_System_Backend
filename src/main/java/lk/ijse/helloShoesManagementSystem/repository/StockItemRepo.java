package lk.ijse.helloShoesManagementSystem.repository;

import lk.ijse.helloShoesManagementSystem.entity.StockItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemRepo extends JpaRepository<StockItemEntity, String> {
}
