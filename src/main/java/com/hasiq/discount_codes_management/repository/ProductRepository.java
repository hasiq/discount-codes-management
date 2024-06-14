package com.hasiq.discount_codes_management.repository;

import com.hasiq.discount_codes_management.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    <S extends ProductRepository> S save(S product);

//    List<ProductEntity> findAll();




}
