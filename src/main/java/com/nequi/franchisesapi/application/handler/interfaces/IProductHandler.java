package com.nequi.franchisesapi.application.handler.interfaces;

import com.nequi.franchisesapi.application.dto.request.ProductRequestDto;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductHandler {

    Mono<Product> saveProduct(ProductRequestDto product);
    Mono<Product> findProductById(Long id);
    Mono<Product> updateProductName(Long id, String name);
    Mono<Void> updateProductStock(Long productId, Long branchId, Integer stock);
    Flux<ProductStockByBranch> getTopStockProductsByBranchByFranchiseId(Long id);
    Mono<Void> deleteProduct(Long productId);
}
