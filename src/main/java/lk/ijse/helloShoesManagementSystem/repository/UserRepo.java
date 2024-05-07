package lk.ijse.helloShoesManagementSystem.repository;

import lk.ijse.helloShoesManagementSystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, String> {
}
