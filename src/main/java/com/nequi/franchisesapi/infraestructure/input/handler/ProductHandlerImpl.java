package com.nequi.franchisesapi.infraestructure.input.handler;

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
}
