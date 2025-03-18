package com.nequi.franchisesapi.infraestructure.out.persistence.adapter;

import com.nequi.franchisesapi.domain.model.BranchProduct;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import com.nequi.franchisesapi.infraestructure.exception.NoDataFoundException;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.BranchProductEntity;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.ProductEntity;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IBranchProductEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IProductEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IBranchProductRepository;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductPersistenceAdapterTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IProductEntityMapper productEntityMapper;

    @Mock
    private IBranchProductEntityMapper branchProductEntityMapper;

    @Mock
    private IBranchProductRepository branchProductRepository;

    @InjectMocks
    private ProductPersistenceAdapter productPersistenceAdapter;

    private Product product;
    private ProductEntity productEntity;
    private BranchProduct branchProduct;
    private BranchProductEntity branchProductEntity;
    private ProductStockByBranch productStockByBranch;

    @BeforeEach
    void setUp() {
        // Configurar producto
        product = new Product();
        product.setId(1L);
        product.setName("Producto Test");

        // Configurar entidad de producto
        productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Producto Test");

        // Configurar branch product
        branchProduct = new BranchProduct();
        branchProduct.setProductId(1L);
        branchProduct.setBranchId(2L);
        branchProduct.setStock(10);

        // Configurar entidad branch product
        branchProductEntity = new BranchProductEntity();
        branchProductEntity.setProductId(1L);
        branchProductEntity.setBranchId(2L);
        branchProductEntity.setStock(10);

        // Configurar ProductStockByBranch
        productStockByBranch = new ProductStockByBranch(1L, "Producto Test", 2L, "Sucursal Test", 10);
    }

    @Test
    void saveProduct_shouldSaveProductAndReturnSavedProduct() {
        
        when(productEntityMapper.toEntity(any(Product.class))).thenReturn(productEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(Mono.just(productEntity));
        when(productEntityMapper.toModel(any(ProductEntity.class))).thenReturn(product);

        Mono<Product> result = productPersistenceAdapter.saveProduct(product);

        StepVerifier.create(result)
                .expectNextMatches(savedProduct -> {
                    assertEquals(product.getId(), savedProduct.getId());
                    assertEquals(product.getName(), savedProduct.getName());
                    return true;
                })
                .verifyComplete();

        verify(productEntityMapper).toEntity(product);
        verify(productRepository).save(productEntity);
        verify(productEntityMapper).toModel(productEntity);
    }

    @Test
    void findProductById_shouldReturnProduct_whenProductExists() {
        
        when(productRepository.findById(anyLong())).thenReturn(Mono.just(productEntity));
        when(productEntityMapper.toModel(any(ProductEntity.class))).thenReturn(product);

        Mono<Product> result = productPersistenceAdapter.findProductById(1L);

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();

        verify(productRepository).findById(1L);
        verify(productEntityMapper).toModel(productEntity);
    }

    @Test
    void findProductById_shouldReturnEmptyMono_whenProductDoesNotExist() {
        
        when(productRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<Product> result = productPersistenceAdapter.findProductById(99L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(productRepository).findById(99L);
        verify(productEntityMapper, never()).toModel(any());
    }

    @Test
    void updateProductName_shouldUpdateAndReturnProduct() {
        
        when(productRepository.updateName(anyLong(), anyString())).thenReturn(Mono.empty());
        when(productRepository.findById(anyLong())).thenReturn(Mono.just(productEntity));
        when(productEntityMapper.toModel(any(ProductEntity.class))).thenReturn(product);

        Mono<Product> result = productPersistenceAdapter.updateProductName(1L, "Nuevo Nombre");

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();

        verify(productRepository).updateName(1L, "Nuevo Nombre");
        verify(productRepository).findById(1L);
        verify(productEntityMapper).toModel(productEntity);
    }

    @Test
    void updateProductName_shouldThrowException_whenProductNotFound() {
        
        when(productRepository.updateName(anyLong(), anyString())).thenReturn(Mono.empty());
        when(productRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<Product> result = productPersistenceAdapter.updateProductName(99L, "Nuevo Nombre");

        StepVerifier.create(result)
                .expectError(NoDataFoundException.class)
                .verify();

        verify(productRepository).updateName(99L, "Nuevo Nombre");
        verify(productRepository).findById(99L);
    }

    @Test
    void deleteProduct_shouldDeleteProductAndReturnVoid() {
        
        when(branchProductRepository.deleteByProductId(anyLong())).thenReturn(Mono.empty());
        when(productRepository.deleteById(anyLong())).thenReturn(Mono.empty());

        Mono<Void> result = productPersistenceAdapter.deleteProduct(1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(branchProductRepository).deleteByProductId(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void updateProductStock_shouldUpdateStockAndReturnVoid() {
        
        when(branchProductRepository.updateProductStock(anyLong(), anyLong(), anyInt())).thenReturn(Mono.empty());

        Mono<Void> result = productPersistenceAdapter.updateProductStock(1L, 2L, 15);

        StepVerifier.create(result)
                .verifyComplete();

        verify(branchProductRepository).updateProductStock(1L, 2L, 15);
    }

    @Test
    void saveBranchProduct_shouldSaveBranchProductAndReturnSaved() {
        
        when(branchProductEntityMapper.toEntity(any(BranchProduct.class))).thenReturn(branchProductEntity);
        when(branchProductRepository.save(any(BranchProductEntity.class))).thenReturn(Mono.just(branchProductEntity));
        when(branchProductEntityMapper.toModel(any(BranchProductEntity.class))).thenReturn(branchProduct);

        Mono<BranchProduct> result = productPersistenceAdapter.saveBranchProduct(branchProduct);

        StepVerifier.create(result)
                .expectNextMatches(saved -> {
                    assertEquals(branchProduct.getProductId(), saved.getProductId());
                    assertEquals(branchProduct.getBranchId(), saved.getBranchId());
                    assertEquals(branchProduct.getStock(), saved.getStock());
                    return true;
                })
                .verifyComplete();

        verify(branchProductEntityMapper).toEntity(branchProduct);
        verify(branchProductRepository).save(branchProductEntity);
        verify(branchProductEntityMapper).toModel(branchProductEntity);
    }

    @Test
    void getTopStockProductsByBranchByFranchiseId_shouldReturnProductsList() {
        
        when(productRepository.topStockProductsByBranchByFranchiseId(anyLong()))
                .thenReturn(Flux.just(productStockByBranch));

        Flux<ProductStockByBranch> result = productPersistenceAdapter.getTopStockProductsByBranchByFranchiseId(1L);

        StepVerifier.create(result)
                .expectNext(productStockByBranch)
                .verifyComplete();

        verify(productRepository).topStockProductsByBranchByFranchiseId(1L);
    }

    @Test
    void findProductByName_shouldReturnProduct_whenProductExists() {
        
        when(productRepository.findProductEntityByName(anyString())).thenReturn(Mono.just(productEntity));
        when(productEntityMapper.toModel(any(ProductEntity.class))).thenReturn(product);

        Mono<Product> result = productPersistenceAdapter.findProductByName("Producto Test");

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();

        verify(productRepository).findProductEntityByName("Producto Test");
        verify(productEntityMapper).toModel(productEntity);
    }

    @Test
    void findProductByName_shouldReturnEmptyMono_whenProductDoesNotExist() {
        
        when(productRepository.findProductEntityByName(anyString())).thenReturn(Mono.empty());

        Mono<Product> result = productPersistenceAdapter.findProductByName("Producto Inexistente");

        StepVerifier.create(result)
                .verifyComplete();

        verify(productRepository).findProductEntityByName("Producto Inexistente");
        verify(productEntityMapper, never()).toModel(any());
    }
}