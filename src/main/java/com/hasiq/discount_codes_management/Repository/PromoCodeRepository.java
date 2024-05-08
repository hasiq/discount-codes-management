package com.hasiq.discount_codes_management.Repository;

import com.hasiq.discount_codes_management.Entity.PromoCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCodeEntity, String> {

    @Override
    List<PromoCodeEntity> findAll();

    @Override
    PromoCodeEntity getOne(String s);

    @Override
    <S extends PromoCodeEntity> S save(S s);
}
