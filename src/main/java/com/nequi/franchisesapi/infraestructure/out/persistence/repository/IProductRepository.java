package com.nequi.franchisesapi.infraestructure.out.persistence.repository;

import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {

    @Query("UPDATE products SET name = :name WHERE id = :id")
    Mono<Integer> updateName(Long id, String name);

    @Query("SELECT b.id AS branch_id, b.name AS branch_name, p.id AS product_id, p.name AS product_name, bp.stock " +
            "FROM branches b " +
            "JOIN branch_products bp ON b.id = bp.branch_id " +
            "JOIN products p ON bp.product_id = p.id " +
            "WHERE b.franchise_id = :id " +
            "AND bp.stock = ( " +
            "SELECT MAX(bp2.stock) " +
            "FROM branch_products bp2 " +
            "WHERE bp2.branch_id = b.id );")
    Flux<ProductStockByBranch> topStockProductsByBranchByFranchiseId(Long id);

    Mono<ProductEntity> findProductEntityByName(String name);
}


