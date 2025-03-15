package com.nequi.franchisesapi.domain.spi;

import com.nequi.franchisesapi.domain.model.Product;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {
    Mono<Product> saveProduct(Product product);
    Mono<Product> findProductById(Long id);
    Mono<Product> updateProductName(Long id, String name);
    Mono<Void> deleteProduct(Long id);
}
