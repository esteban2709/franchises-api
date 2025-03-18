package com.nequi.franchisesapi.infraestructure.input.handler;

import com.nequi.franchisesapi.application.dto.request.ProductRequestDto;
import com.nequi.franchisesapi.application.handler.interfaces.IProductHandler;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import com.nequi.franchisesapi.infraestructure.exeptionhandler.ExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductHandlerImplTest {

    @Mock
    private IProductHandler productHandler;

    @InjectMocks
    private ProductHandlerImpl productHandlerImpl;

    private ProductRequestDto productRequestDto;
    private Product product;
    private ProductStockByBranch productStockByBranch;

    @BeforeEach
    void setUp() {
        // Inicializar objetos para pruebas
        productRequestDto = new ProductRequestDto();
        productRequestDto.setName("Test Product");
        productRequestDto.setBranchId(1L);
        productRequestDto.setStock(10);

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setBranchId(1L);
        product.setStock(10);

        productStockByBranch = new ProductStockByBranch();
        productStockByBranch.setProductId(1L);
        productStockByBranch.setProductName("Test Product");
        productStockByBranch.setBranchId(1L);
        productStockByBranch.setBranchName("Test Branch");
        productStockByBranch.setStock(10);
    }

    @Test
    void createProduct_shouldReturnOkResponse() {
        
        ServerRequest request = MockServerRequest.builder()
                .body(Mono.just(productRequestDto));

        when(productHandler.saveProduct(any(ProductRequestDto.class)))
                .thenReturn(Mono.just(product));

        
        Mono<ServerResponse> response = productHandlerImpl.createProduct(request);

        
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(productHandler).saveProduct(any(ProductRequestDto.class));
    }

    @Test
    void updateProductStock_shouldReturnOkResponse() {
        
        ServerRequest request = MockServerRequest.builder()
                .queryParam("productId", "1")
                .queryParam("branchId", "1")
                .queryParam("stock", "20")
                .build();

        when(productHandler.updateProductStock(anyLong(), anyLong(), anyInt()))
                .thenReturn(Mono.empty());

        
        Mono<ServerResponse> response = productHandlerImpl.updateProductStock(request);

        
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(productHandler).updateProductStock(1L, 1L, 20);
    }

    @Test
    void updateProductStock_shouldThrowExceptionWhenProductIdIsMissing() {
        
        ServerRequest request = MockServerRequest.builder()
                .queryParam("branchId", "1")
                .queryParam("stock", "20")
                .build();

        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productHandlerImpl.updateProductStock(request).block());

        assertEquals(ExceptionResponse.PRODUCT_ID_REQUIRED.getMessage(), exception.getMessage());
        verify(productHandler, never()).updateProductStock(anyLong(), anyLong(), anyInt());
    }

    @Test
    void updateProductStock_shouldThrowExceptionWhenBranchIdIsMissing() {
        
        ServerRequest request = MockServerRequest.builder()
                .queryParam("productId", "1")
                .queryParam("stock", "20")
                .build();

        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productHandlerImpl.updateProductStock(request).block());

        assertEquals(ExceptionResponse.BRANCH_ID_REQUIRED.getMessage(), exception.getMessage());
        verify(productHandler, never()).updateProductStock(anyLong(), anyLong(), anyInt());
    }

    @Test
    void updateProductStock_shouldThrowExceptionWhenStockIsMissing() {
        
        ServerRequest request = MockServerRequest.builder()
                .queryParam("productId", "1")
                .queryParam("branchId", "1")
                .build();

        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productHandlerImpl.updateProductStock(request).block());

        assertEquals(ExceptionResponse.STOCK_REQUIRED.getMessage(), exception.getMessage());
        verify(productHandler, never()).updateProductStock(anyLong(), anyLong(), anyInt());
    }

    @Test
    void getTopStockProductsByBranchByFranchiseId_shouldReturnOkResponse() {
        
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .build();

        List<ProductStockByBranch> productList = Arrays.asList(productStockByBranch);

        when(productHandler.getTopStockProductsByBranchByFranchiseId(anyLong()))
                .thenReturn(Flux.fromIterable(productList));

        
        Mono<ServerResponse> response = productHandlerImpl.getTopStockProductsByBranchByFranchiseId(request);

        
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(productHandler).getTopStockProductsByBranchByFranchiseId(1L);
    }

    @Test
    void updateProductName_shouldReturnOkResponse() {
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .queryParam("name", "Updated Product Name")
                .build();

        when(productHandler.updateProductName(anyLong(), anyString()))
                .thenReturn(Mono.just(product));

        Mono<ServerResponse> response = productHandlerImpl.updateProductName(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(productHandler).updateProductName(1L, "Updated Product Name");
    }

    @Test
    void updateProductName_shouldThrowExceptionWhenNameIsMissing() {
        
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .build();

        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productHandlerImpl.updateProductName(request).block());

        assertEquals(ExceptionResponse.NAME_REQUIRED.getMessage(), exception.getMessage());
        verify(productHandler, never()).updateProductName(anyLong(), anyString());
    }

    @Test
    void deleteProduct_shouldReturnOkResponse() {
        
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .build();

        when(productHandler.deleteProduct(anyLong()))
                .thenReturn(Mono.empty());

        
        Mono<ServerResponse> response = productHandlerImpl.deleteProduct(request);

        
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(productHandler).deleteProduct(1L);
    }
}