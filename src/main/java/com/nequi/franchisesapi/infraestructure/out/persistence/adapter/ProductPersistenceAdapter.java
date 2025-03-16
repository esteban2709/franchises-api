package com.nequi.franchisesapi.infraestructure.out.persistence.adapter;

import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.spi.IProductPersistencePort;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.ProductEntity;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IProductEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class ProductPersistenceAdapter implements IProductPersistencePort {

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public Mono<Product> saveProduct(Product product) {
        ProductEntity productEntity = productEntityMapper.toEntity(product);
        return productRepository.save(productEntity)
                .map(productEntityMapper::toModel);
    }

    @Override
    public Mono<Product> findProductById(Long id) {
        return productRepository.findById(id)
                .map(productEntityMapper::toModel);
    }

    @Override
    public Mono<Product> updateProductName(Long id, String name) {
        return productRepository.updateName(id, name)
                .map(productEntityMapper::toModel);
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }
}
