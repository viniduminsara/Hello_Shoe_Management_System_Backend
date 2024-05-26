package lk.ijse.helloShoesManagementSystem.repository;

import lk.ijse.helloShoesManagementSystem.dto.InventoryDTO;
import lk.ijse.helloShoesManagementSystem.dto.VerityTypeQuantity;
import lk.ijse.helloShoesManagementSystem.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<ItemEntity, String> {
    @Query("SELECT itemCode FROM ItemEntity  WHERE itemCode LIKE CONCAT(:prefix, '%') ORDER BY itemCode DESC LIMIT 1")
    String findLastItemCodeWithPrefix(@Param("prefix") String prefix);

    @Query("SELECT i FROM ItemEntity i JOIN i.itemSizeEntities ise JOIN ise.saleDetailsEntities sde GROUP BY i.itemCode ORDER BY COUNT(sde.id) DESC")
    List<ItemEntity> findMostSoldItems();

    @Query("SELECT new lk.ijse.helloShoesManagementSystem.dto.VerityTypeQuantity(i.verityType, SUM(s.qty)) FROM ItemEntity i JOIN i.itemSizeEntities s GROUP BY i.verityType")
    List<VerityTypeQuantity> getVerityTypeQuantity();

}
