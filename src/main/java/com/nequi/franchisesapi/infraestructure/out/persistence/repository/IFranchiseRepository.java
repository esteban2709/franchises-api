package com.nequi.franchisesapi.infraestructure.out.persistence.repository;

import com.nequi.franchisesapi.infraestructure.out.persistence.entity.FranchiseEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IFranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {

     @Query("UPDATE franchises SET name = :name WHERE id = :id")
     Mono<Integer> updateName(Long id, String name);
}
