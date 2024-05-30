package lk.ijse.helloShoesManagementSystem.repository;

import lk.ijse.helloShoesManagementSystem.entity.SaleDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleDetailsRepo extends JpaRepository<SaleDetailsEntity, String> {

    @Query("SELECT sd FROM SaleDetailsEntity sd WHERE sd.saleEntity.orderId = :orderId")
    List<SaleDetailsEntity> findSaleDetailsByOrderId(@Param("orderId") String orderId);

}
