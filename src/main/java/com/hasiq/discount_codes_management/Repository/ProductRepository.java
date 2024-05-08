package com.hasiq.discount_codes_management.Repository;

import com.hasiq.discount_codes_management.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    <S extends ProductRepository> S save(S product);

    List<ProductEntity> findAll();


}
