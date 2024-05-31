package lk.ijse.helloShoesManagementSystem.repository;

import lk.ijse.helloShoesManagementSystem.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByContact(String contact);

    @Query("SELECT MAX(s.date) FROM SaleEntity s WHERE s.customer.customerId = :customerId")
    Timestamp findLastSaleDateByCustomerId(@Param("customerId") String customerId);

    @Query("SELECT SUM(s.addedPoints) FROM SaleEntity s WHERE s.customer.customerId = :customerId")
    Double findTotalPointsByCustomerId(@Param("customerId") String customerId);

}
