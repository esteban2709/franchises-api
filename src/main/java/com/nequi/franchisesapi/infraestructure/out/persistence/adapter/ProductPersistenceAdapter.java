package com.nequi.franchisesapi.infraestructure.out.persistence.adapter;

import com.nequi.franchisesapi.domain.model.BranchProduct;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.spi.IProductPersistencePort;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import com.nequi.franchisesapi.infraestructure.exception.NoDataFoundException;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.BranchProductEntity;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.ProductEntity;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IBranchProductEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IProductEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IBranchProductRepository;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class ProductPersistenceAdapter implements IProductPersistencePort {

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;
    private final IBranchProductEntityMapper branchProductEntityMapper;
    private final IBranchProductRepository branchProductRepository;

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
                .then(productRepository.findById(id))
                .map(productEntityMapper::toModel)
                .switchIfEmpty(Mono.error(new NoDataFoundException()));
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }

    @Override
    public Mono<Void> updateProductStock(Long productId, Long branchId, Integer stock) {
        return branchProductRepository.updateProductStock(productId, branchId, stock);
    }

    @Override
    public Mono<BranchProduct> saveBranchProduct(BranchProduct branchProduct) {
        BranchProductEntity branchProductEntity = branchProductEntityMapper.toEntity(branchProduct);
        return branchProductRepository.save(branchProductEntity).map(branchProductEntityMapper::toModel);
    }

    @Override
    public Flux<ProductStockByBranch> getTopStockProductsByBranchByFranchiseId(Long id) {
        return productRepository.topStockProductsByBranchByFranchiseId(id);
    }

    @Override
    public Mono<Product> findProductByName(String name) {
        return productRepository.findProductEntityByName(name)
                .map(productEntityMapper::toModel)
                .switchIfEmpty(Mono.empty());
    }
}
