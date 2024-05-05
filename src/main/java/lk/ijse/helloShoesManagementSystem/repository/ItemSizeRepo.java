package lk.ijse.helloShoesManagementSystem.repository;

import lk.ijse.helloShoesManagementSystem.entity.ItemSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSizeRepo extends JpaRepository<ItemSizeEntity, String> {
}
