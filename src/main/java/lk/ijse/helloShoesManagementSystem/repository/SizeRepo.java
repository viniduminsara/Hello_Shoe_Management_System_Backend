package lk.ijse.helloShoesManagementSystem.repository;

import lk.ijse.helloShoesManagementSystem.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepo extends JpaRepository<SizeEntity, String> {
//    @Query("SELECT s FROM SizeEntity s WHERE s.size = :size")
    Optional<SizeEntity> findBySize(@Param("size") Integer size);
}
