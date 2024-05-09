package com.hasiq.discount_codes_management.Repository;

import com.hasiq.discount_codes_management.Entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {

    <S extends PurchaseEntity> S save(S entity);
}
