package com.nequi.franchisesapi.application.handler.interfaces;

import com.nequi.franchisesapi.application.dto.request.ProductRequestDto;
import com.nequi.franchisesapi.domain.model.Product;
import reactor.core.publisher.Mono;

public interface IProductHandler {

    Mono<Product> saveProduct(ProductRequestDto product);
    Mono<Product> findProductById(Long id);
    Mono<Product> updateProductName(Long id, String name);
}
