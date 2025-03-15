package com.nequi.franchisesapi.infraestructure.input.handler;

import com.nequi.franchisesapi.application.dto.request.FranchiseRequestDto;
import com.nequi.franchisesapi.application.mapper.FranchiseRequestMapper;
import com.nequi.franchisesapi.domain.api.IFranchiseServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseHandlerImpl {

    private final IFranchiseServicePort franchiseServicePort;
    private final FranchiseRequestMapper franchiseRequestMapper;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(FranchiseRequestDto.class)
                .map(franchiseRequestMapper::toModel)
                .flatMap(franchiseServicePort::saveFranchise)
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
//                .onErrorResume(e -> ServerResponse.badRequest()
//                        .bodyValue("Error creating franchise: " + e.getMessage()))
                ;
    }
}
