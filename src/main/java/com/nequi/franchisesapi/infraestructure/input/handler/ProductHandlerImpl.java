package com.nequi.franchisesapi.infraestructure.input.handler;

import com.nequi.franchisesapi.application.dto.request.BranchProductRequestDto;
import com.nequi.franchisesapi.application.dto.request.ProductRequestDto;
import com.nequi.franchisesapi.application.handler.interfaces.IProductHandler;
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
//                .onErrorResume(e -> ServerResponse.badRequest()
//                        .bodyValue("Error creating product: " + e.getMessage()))
                ;
    }

    public Mono<ServerResponse> updateProductStock(ServerRequest request) {
        Long productId = Long.valueOf(request.queryParam("productId").orElseThrow(
                () -> new IllegalArgumentException("Branch ID is required")
        ));
        Long branchId = Long.valueOf(request.queryParam("branchId").orElseThrow(
                () -> new IllegalArgumentException("Branch ID is required")
        ));
        Integer stock = Integer.valueOf(request.queryParam("stock").orElseThrow(
                () -> new IllegalArgumentException("Stock is required")
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
}
