package com.nequi.franchisesapi.domain.usecase;

import com.nequi.franchisesapi.domain.model.BranchProduct;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.spi.IProductPersistencePort;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import com.nequi.franchisesapi.domain.utils.validations.Validations;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {

    @Mock
    private IProductPersistencePort productPersistencePort;

    @Mock
    private Validations validations;

    @InjectMocks
    private ProductUseCase productUseCase;

    private Product product;
    private BranchProduct branchProduct;
    private ProductStockByBranch productStockByBranch;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Producto Test");
        product.setBranchId(2L);
        product.setStock(10);

        branchProduct = new BranchProduct();
        branchProduct.setId(1L);
        branchProduct.setBranchId(2L);
        branchProduct.setProductId(1L);
        branchProduct.setStock(10);

        productStockByBranch = new ProductStockByBranch();
        productStockByBranch.setProductId(1L);
        productStockByBranch.setProductName("Producto Test");
        productStockByBranch.setBranchId(2L);
        productStockByBranch.setBranchName("Sucursal Test");
        productStockByBranch.setStock(10);
    }

    @Test
    void saveProduct_whenProductDoesNotExist_shouldSaveProductAndCreateRelation() {
        when(validations.existBranch(anyLong())).thenReturn(Mono.just(true));
        when(validations.existProductByName(anyString())).thenReturn(Mono.empty());
        when(productPersistencePort.saveProduct(any(Product.class))).thenReturn(Mono.just(product));
        when(productPersistencePort.saveBranchProduct(any(BranchProduct.class))).thenReturn(Mono.just(branchProduct));

        StepVerifier.create(productUseCase.saveProduct(product))
                .expectNextMatches(savedProduct -> {
                    assertEquals(1L, savedProduct.getId());
                    assertEquals("Producto Test", savedProduct.getName());
                    assertEquals(2L, savedProduct.getBranchId());
                    assertEquals(10, savedProduct.getStock());
                    return true;
                })
                .verifyComplete();

        verify(validations).existBranch(2L);
        verify(validations).existProductByName("Producto Test");
        verify(productPersistencePort).saveProduct(any(Product.class));
        verify(productPersistencePort).saveBranchProduct(any(BranchProduct.class));
    }

    @Test
    void findProductById_shouldReturnProduct() {
        when(productPersistencePort.findProductById(anyLong())).thenReturn(Mono.just(product));

        StepVerifier.create(productUseCase.findProductById(1L))
                .expectNextMatches(foundProduct -> {
                    assertEquals(1L, foundProduct.getId());
                    assertEquals("Producto Test", foundProduct.getName());
                    assertEquals(2L, foundProduct.getBranchId());
                    assertEquals(10, foundProduct.getStock());
                    return true;
                })
                .verifyComplete();

        verify(productPersistencePort).findProductById(1L);
    }

    @Test
    void updateProductName_shouldUpdateAndReturnProduct() {
        when(validations.existProduct(anyLong())).thenReturn(Mono.just(true));
        when(productPersistencePort.updateProductName(anyLong(), anyString())).thenReturn(Mono.just(product));

        StepVerifier.create(productUseCase.updateProductName(1L, "Producto Actualizado"))
                .expectNextMatches(updatedProduct -> {
                    assertEquals(1L, updatedProduct.getId());
                    assertEquals("Producto Test", updatedProduct.getName());  // El mock devuelve el objeto sin cambiar
                    assertEquals(2L, updatedProduct.getBranchId());
                    assertEquals(10, updatedProduct.getStock());
                    return true;
                })
                .verifyComplete();

        verify(validations).existProduct(1L);
        verify(productPersistencePort).updateProductName(1L, "Producto Actualizado");
    }

    @Test
    void deleteProduct_shouldDeleteProduct() {
        when(productPersistencePort.deleteProduct(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.deleteProduct(1L))
                .verifyComplete();

        verify(productPersistencePort).deleteProduct(1L);
    }

    @Test
    void updateProductStock_shouldUpdateStock() {
        when(validations.existProduct(anyLong())).thenReturn(Mono.just(true));
        when(validations.existBranch(anyLong())).thenReturn(Mono.just(true));
        when(productPersistencePort.updateProductStock(anyLong(), anyLong(), anyInt())).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.updateProductStock(1L, 2L, 20))
                .verifyComplete();

        verify(validations).existProduct(1L);
        verify(validations).existBranch(2L);
        verify(productPersistencePort).updateProductStock(1L, 2L, 20);
    }

    @Test
    void saveBranchProduct_shouldSaveAndReturnBranchProduct() {
        when(productPersistencePort.saveBranchProduct(any(BranchProduct.class))).thenReturn(Mono.just(branchProduct));

        StepVerifier.create(productUseCase.saveBranchProduct(branchProduct))
                .expectNextMatches(savedBranchProduct -> {
                    assertEquals(1L, savedBranchProduct.getId());
                    assertEquals(2L, savedBranchProduct.getBranchId());
                    assertEquals(1L, savedBranchProduct.getProductId());
                    assertEquals(10, savedBranchProduct.getStock());
                    return true;
                })
                .verifyComplete();

        verify(productPersistencePort).saveBranchProduct(branchProduct);
    }

    @Test
    void getTopStockProductsByBranchByFranchiseId_shouldReturnProductsList() {
        when(productPersistencePort.getTopStockProductsByBranchByFranchiseId(anyLong()))
                .thenReturn(Flux.just(productStockByBranch));

        StepVerifier.create(productUseCase.getTopStockProductsByBranchByFranchiseId(1L))
                .expectNextMatches(stockByBranch -> {
                    assertEquals(1L, stockByBranch.getProductId());
                    assertEquals("Producto Test", stockByBranch.getProductName());
                    assertEquals(2L, stockByBranch.getBranchId());
                    assertEquals("Sucursal Test", stockByBranch.getBranchName());
                    assertEquals(10, stockByBranch.getStock());
                    return true;
                })
                .verifyComplete();

        verify(productPersistencePort).getTopStockProductsByBranchByFranchiseId(1L);
    }
}