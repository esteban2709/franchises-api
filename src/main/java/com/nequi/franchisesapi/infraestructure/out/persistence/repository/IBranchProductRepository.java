package com.nequi.franchisesapi.infraestructure.out.persistence.repository;

import com.nequi.franchisesapi.infraestructure.out.persistence.entity.BranchProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IBranchProductRepository extends ReactiveCrudRepository<BranchProductEntity, Long> {

    @Query("UPDATE branch_products SET stock = :stock WHERE product_id = :productId and branch_id = :branchId")
    Mono<Void> updateProductStock(Long productId, Long branchId, Integer stock);

    @Query("DELETE FROM branch_products WHERE product_id = :productId")
    Mono<Void> deleteByProductId(Long productId);
}
