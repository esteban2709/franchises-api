package com.nequi.franchisesapi.infraestructure.out.persistence.repository;

import com.nequi.franchisesapi.infraestructure.out.persistence.entity.BranchEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IBranchRepository extends ReactiveCrudRepository<BranchEntity, Long> {

    @Query("UPDATE branches SET name = :name WHERE id = :id")
    Mono<Integer> updateName(Long id, String name);

    Flux<BranchEntity> findByFranchiseId(Long franchiseId);

}
