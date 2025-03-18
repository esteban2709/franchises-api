package com.nequi.franchisesapi.application.handler.impl;

import com.nequi.franchisesapi.application.dto.request.ProductRequestDto;
import com.nequi.franchisesapi.application.mapper.IBranchProductRequestMapper;
import com.nequi.franchisesapi.application.mapper.IProductRequestMapper;
import com.nequi.franchisesapi.domain.api.IProductServicePort;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductHandlerTest {

    @Mock
    private IProductServicePort productServicePort;

    @Mock
    private IProductRequestMapper productRequestMapper;

    @Mock
    private IBranchProductRequestMapper branchProductRequestMapper;

    @InjectMocks
    private ProductHandler productHandler;

    private ProductRequestDto productRequestDto;
    private Product product;
    private ProductStockByBranch productStockByBranch;

    @BeforeEach
    void setUp() {
        // Inicializar objetos para pruebas
        productRequestDto = new ProductRequestDto();
        productRequestDto.setName("Producto Test");
        productRequestDto.setBranchId(1L);
        productRequestDto.setStock(10);

        product = new Product();
        product.setId(1L);
        product.setName("Producto Test");
        product.setBranchId(1L);
        product.setStock(10);

        productStockByBranch = new ProductStockByBranch();
        productStockByBranch.setProductId(1L);
        productStockByBranch.setProductName("Producto Test");
        productStockByBranch.setBranchId(1L);
        productStockByBranch.setBranchName("Sucursal Test");
        productStockByBranch.setStock(10);
    }

    @Test
    void saveProduct_shouldReturnSavedProduct() {
        when(productRequestMapper.toModel(any(ProductRequestDto.class))).thenReturn(product);
        when(productServicePort.saveProduct(any(Product.class))).thenReturn(Mono.just(product));

        StepVerifier.create(productHandler.saveProduct(productRequestDto))
                .expectNext(product)
                .verifyComplete();

        verify(productRequestMapper).toModel(productRequestDto);
        verify(productServicePort).saveProduct(product);
    }

    @Test
    void findProductById_shouldReturnProduct() {
        when(productServicePort.findProductById(anyLong())).thenReturn(Mono.just(product));

        StepVerifier.create(productHandler.findProductById(1L))
                .expectNext(product)
                .verifyComplete();

        verify(productServicePort).findProductById(1L);
    }

    @Test
    void updateProductName_shouldReturnUpdatedProduct() {
        when(productServicePort.updateProductName(anyLong(), anyString())).thenReturn(Mono.just(product));

        StepVerifier.create(productHandler.updateProductName(1L, "Producto Actualizado"))
                .expectNext(product)
                .verifyComplete();

        verify(productServicePort).updateProductName(1L, "Producto Actualizado");
    }

    @Test
    void updateProductStock_shouldCompleteSuccessfully() {
        when(productServicePort.updateProductStock(anyLong(), anyLong(), anyInt()))
                .thenReturn(Mono.empty());

        StepVerifier.create(productHandler.updateProductStock(1L, 1L, 20))
                .verifyComplete();

        verify(productServicePort).updateProductStock(1L, 1L, 20);
    }

    @Test
    void getTopStockProductsByBranchByFranchiseId_shouldReturnProductsList() {
        List<ProductStockByBranch> productList = Arrays.asList(productStockByBranch);
        when(productServicePort.getTopStockProductsByBranchByFranchiseId(anyLong()))
                .thenReturn(Flux.fromIterable(productList));

        StepVerifier.create(productHandler.getTopStockProductsByBranchByFranchiseId(1L))
                .expectNext(productStockByBranch)
                .verifyComplete();

        verify(productServicePort).getTopStockProductsByBranchByFranchiseId(1L);
    }

    @Test
    void deleteProduct_shouldCompleteSuccessfully() {
        when(productServicePort.deleteProduct(anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier.create(productHandler.deleteProduct(1L))
                .verifyComplete();

        verify(productServicePort).deleteProduct(1L);
    }
}