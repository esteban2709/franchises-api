package com.nequi.franchisesapi.application.handler.impl;

import com.nequi.franchisesapi.application.dto.request.ProductRequestDto;
import com.nequi.franchisesapi.application.handler.interfaces.IProductHandler;
import com.nequi.franchisesapi.application.mapper.IProductRequestMapper;
import com.nequi.franchisesapi.domain.api.IProductServicePort;
import com.nequi.franchisesapi.domain.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductHandler implements IProductHandler {

    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;

    @Override
    public Mono<Product> saveProduct(ProductRequestDto product) {
        return productServicePort.saveProduct(productRequestMapper.toModel(product));
    }

    @Override
    public Mono<Product> findProductById(Long id) {
        return productServicePort.findProductById(id);
    }

    @Override
    public Mono<Product> updateProductName(Long id, String name) {
        return productServicePort.updateProductName(id, name);
    }
}
