package com.nequi.franchisesapi.domain.usecase;

import com.nequi.franchisesapi.domain.api.IProductServicePort;
import com.nequi.franchisesapi.domain.model.BranchProduct;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.spi.IProductPersistencePort;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import com.nequi.franchisesapi.domain.utils.validations.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;
    private final Validations validations;


    @Override
    public Mono<Product> saveProduct(Product product) {
        return validations.existBranch(product.getBranchId())
                .then(Mono.defer(() -> validations.existProductByName(product.getName())
                        .flatMap(existingProduct -> createBranchProductRelation(existingProduct, product))
                        .switchIfEmpty(
                                productPersistencePort.saveProduct(product)
                                        .flatMap(savedProduct -> createBranchProductRelation(savedProduct, product))
                        )));
    }

    private Mono<Product> createBranchProductRelation(Product savedProduct, Product originalProduct) {
        BranchProduct branchProduct = new BranchProduct();
        branchProduct.setBranchId(originalProduct.getBranchId());
        branchProduct.setProductId(savedProduct.getId());
        branchProduct.setStock(originalProduct.getStock());

        savedProduct.setStock(originalProduct.getStock());
        savedProduct.setBranchId(originalProduct.getBranchId());

        return saveBranchProduct(branchProduct)
                .thenReturn(savedProduct);
    }

    @Override
    public Mono<Product> findProductById(Long id) {
        return productPersistencePort.findProductById(id);
    }

    @Override
    public Mono<Product> updateProductName(Long id, String name) {
        return validations.existProduct(id)
                .then(Mono.defer(() -> productPersistencePort.updateProductName(id, name)));
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return productPersistencePort.deleteProduct(id);
    }

    @Override
    public Mono<Void> updateProductStock(Long productId, Long branchId, Integer stock) {
        return Mono.when(
                validations.existProduct(productId),
                validations.existBranch(branchId)
        ).then(Mono.defer(() -> productPersistencePort.updateProductStock(productId, branchId, stock)));
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
