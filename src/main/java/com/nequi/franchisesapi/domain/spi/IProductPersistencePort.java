package com.nequi.franchisesapi.domain.spi;

import com.nequi.franchisesapi.domain.model.BranchProduct;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {
    Mono<Product> saveProduct(Product product);
    Mono<Product> findProductById(Long id);
    Mono<Product> updateProductName(Long id, String name);
    Mono<Void> deleteProduct(Long id);
    Mono<Void> updateProductStock(Long productId, Long branchId, Integer stock);
    Mono<BranchProduct> saveBranchProduct(BranchProduct branchProduct);
    Flux<ProductStockByBranch> getTopStockProductsByBranchByFranchiseId(Long id);

}
