package com.nequi.franchisesapi.infraestructure.input.handler;

import com.nequi.franchisesapi.application.dto.request.BranchProductRequestDto;
import com.nequi.franchisesapi.application.dto.request.ProductRequestDto;
import com.nequi.franchisesapi.application.handler.interfaces.IProductHandler;
import com.nequi.franchisesapi.infraestructure.exeptionhandler.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandlerImpl {

    private final IProductHandler productHandler;

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(ProductRequestDto.class)
                .flatMap(productHandler::saveProduct)
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                ;
    }

    public Mono<ServerResponse> updateProductStock(ServerRequest request) {
        Long productId = Long.valueOf(request.queryParam("productId").orElseThrow(
                () -> new IllegalArgumentException(ExceptionResponse.PRODUCT_ID_REQUIRED.getMessage())
        ));
        Long branchId = Long.valueOf(request.queryParam("branchId").orElseThrow(
                () -> new IllegalArgumentException(ExceptionResponse.BRANCH_ID_REQUIRED.getMessage())
        ));
        Integer stock = Integer.valueOf(request.queryParam("stock").orElseThrow(
                () -> new IllegalArgumentException(ExceptionResponse.STOCK_REQUIRED.getMessage())
        ));
        return productHandler.updateProductStock(productId, branchId, stock)
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> getTopStockProductsByBranchByFranchiseId(ServerRequest request) {
        Long franchiseId = Long.valueOf(request.pathVariable("id"));
        return productHandler.getTopStockProductsByBranchByFranchiseId(franchiseId)
                .collectList()
                .flatMap(products -> ServerResponse.ok().bodyValue(products));
    }

    public Mono<ServerResponse> updateProductName(ServerRequest request) {
        Long productId = Long.valueOf(request.pathVariable("id"));
        String name = request.queryParam("name").orElseThrow(
                () -> new IllegalArgumentException(ExceptionResponse.NAME_REQUIRED.getMessage())
        );
        return productHandler.updateProductName(productId, name)
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Long productId = Long.valueOf(request.pathVariable("id"));
        return productHandler.deleteProduct(productId)
                .then(ServerResponse.ok().build());
    }
}
