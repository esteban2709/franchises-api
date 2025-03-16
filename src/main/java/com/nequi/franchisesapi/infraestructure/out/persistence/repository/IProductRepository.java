package com.nequi.franchisesapi.infraestructure.out.persistence.repository;

import com.nequi.franchisesapi.infraestructure.out.persistence.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {

    @Query("UPDATE ProductEntity SET name = :name WHERE id = :id")
    Mono<ProductEntity> updateName(Long id, String name);
}
