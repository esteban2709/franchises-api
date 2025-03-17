package com.nequi.franchisesapi.infraestructure.input.handler;

import com.nequi.franchisesapi.application.dto.request.FranchiseRequestDto;
import com.nequi.franchisesapi.application.handler.interfaces.IFranchiseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseHandlerImpl {

    private final IFranchiseHandler franchiseHandler;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(FranchiseRequestDto.class)
                .flatMap(franchiseHandler::saveFranchise)
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
//                .onErrorResume(e -> ServerResponse.badRequest()
//                        .bodyValue("Error creating franchise: " + e.getMessage()))
                ;
    }

    public Mono<ServerResponse> updateFranchiseName(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return request.bodyToMono(FranchiseRequestDto.class)
                .flatMap(franchiseRequestDto -> franchiseHandler.updateFranchiseName(id, franchiseRequestDto.getName()))
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise));
    }
}
