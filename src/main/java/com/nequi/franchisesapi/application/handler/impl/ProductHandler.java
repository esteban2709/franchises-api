package com.nequi.franchisesapi.application.handler.impl;

import com.nequi.franchisesapi.application.dto.request.ProductRequestDto;
import com.nequi.franchisesapi.application.handler.interfaces.IProductHandler;
import com.nequi.franchisesapi.application.mapper.IBranchProductRequestMapper;
import com.nequi.franchisesapi.application.mapper.IProductRequestMapper;
import com.nequi.franchisesapi.domain.api.IProductServicePort;
import com.nequi.franchisesapi.domain.model.BranchProduct;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductHandler implements IProductHandler {

    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IBranchProductRequestMapper branchProductRequestMapper;

    @Override
    public Mono<Product> saveProduct(ProductRequestDto product) {
        return productServicePort.saveProduct(productRequestMapper.toModel(product))
               ;
    }

    @Override
    public Mono<Product> findProductById(Long id) {
        return productServicePort.findProductById(id);
    }

    @Override
    public Mono<Product> updateProductName(Long id, String name) {
        return productServicePort.updateProductName(id, name);
    }

    @Override
    public Mono<Void> updateProductStock(Long productId, Long branchId, Integer stock) {
        return productServicePort.updateProductStock(productId, branchId, stock);
    }

    @Override
    public Flux<ProductStockByBranch> getTopStockProductsByBranchByFranchiseId(Long id) {
        return productServicePort.getTopStockProductsByBranchByFranchiseId(id);
    }

    @Override
    public Mono<Void> deleteProduct(Long productId) {
        return productServicePort.deleteProduct(productId);
    }

}
