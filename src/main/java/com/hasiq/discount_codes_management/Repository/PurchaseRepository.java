package com.hasiq.discount_codes_management.Repository;

import com.hasiq.discount_codes_management.DTO.SalesReportDTO;
import com.hasiq.discount_codes_management.Entity.PurchaseEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {


    <S extends PurchaseEntity> S save(S entity);

    List<PurchaseEntity> findAll();

    @Query(nativeQuery = true,value = "SELECT CURRENCY, SUM(DISCOUNT_PRICE) AS totalDiscount, SUM(REGULAR_PRICE) AS totalAmount, COUNT(*) AS numberOfPurchases  FROM PURCHASE_ENTITY GROUP BY CURRENCY")
    List<Object[]> salesReport();

}
