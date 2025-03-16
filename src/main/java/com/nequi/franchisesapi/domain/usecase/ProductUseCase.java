package com.nequi.franchisesapi.domain.usecase;

import com.nequi.franchisesapi.domain.api.IProductServicePort;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
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
}
