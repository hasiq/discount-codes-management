package com.hasiq.discount_codes_management.repository;

import com.hasiq.discount_codes_management.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {


    <S extends PurchaseEntity> S save(S entity);

    List<PurchaseEntity> findAll();

    @Query(nativeQuery = true, value = "SELECT CURRENCY, SUM(DISCOUNT_PRICE) AS totalDiscount, SUM(REGULAR_PRICE) AS totalAmount, COUNT(*) AS numberOfPurchases  FROM PURCHASE_ENTITY GROUP BY CURRENCY")
    List<Object[]> salesReport();


}
