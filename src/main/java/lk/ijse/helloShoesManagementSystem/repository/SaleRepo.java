package lk.ijse.helloShoesManagementSystem.repository;

import lk.ijse.helloShoesManagementSystem.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepo extends JpaRepository<SaleEntity, String> {

    @Query("SELECT MAX(s.orderId) FROM SaleEntity s WHERE s.orderId LIKE 'ORD-%'")
    String findLastOrderId();

    @Query("SELECT SUM(s.total) FROM SaleEntity s")
    Double findTotalSales();

    @Query("SELECT SUM(s.total - sd.itemSizeEntity.itemEntity.buyingPrice * sd.qty) FROM SaleEntity s JOIN s.saleDetailsEntities sd")
    Double findTotalProfit();

}
