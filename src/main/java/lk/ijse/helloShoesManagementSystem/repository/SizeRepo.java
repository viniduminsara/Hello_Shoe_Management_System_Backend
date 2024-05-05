package lk.ijse.helloShoesManagementSystem.repository;

import lk.ijse.helloShoesManagementSystem.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SizeRepo extends JpaRepository<SizeEntity, String> {
    @Query("SELECT s FROM SizeEntity s WHERE s.size = :size")
    Optional<SizeEntity> findBySize(@Param("size") Integer size);
}
