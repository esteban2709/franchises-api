package com.nequi.franchisesapi.domain.usecase;

import com.nequi.franchisesapi.domain.api.IProductServicePort;
import com.nequi.franchisesapi.domain.model.BranchProduct;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.spi.IProductPersistencePort;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;


    @Override
    public Mono<Product> saveProduct(Product product) {
        return productPersistencePort.saveProduct(product);
    }

    @Override
    public Mono<Product> findProductById(Long id) {
        return productPersistencePort.findProductById(id);
    }

    @Override
    public Mono<Product> updateProductName(Long id, String name) {
        return productPersistencePort.updateProductName(id, name);
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return productPersistencePort.deleteProduct(id);
    }

    @Override
    public Mono<Void> updateProductStock(Long productId, Long branchId, Integer stock) {
        return productPersistencePort.updateProductStock(productId, branchId, stock);
    }

    @Override
    public Mono<BranchProduct> saveBranchProduct(BranchProduct branchProduct) {
        return productPersistencePort.saveBranchProduct(branchProduct);
    }

    @Override
    public Flux<ProductStockByBranch> getTopStockProductsByBranchByFranchiseId(Long id) {
        return productPersistencePort.getTopStockProductsByBranchByFranchiseId(id);
    }
}
