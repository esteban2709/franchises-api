package com.nequi.franchisesapi.infraestructure.input.handler;

import com.nequi.franchisesapi.application.dto.request.BranchRequestDto;
import com.nequi.franchisesapi.application.handler.interfaces.IBranchHandler;
import com.nequi.franchisesapi.infraestructure.exeptionhandler.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BranchHandlerImpl {

    private final IBranchHandler branchHandler;

    public Mono<ServerResponse> createBranch(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BranchRequestDto.class)
                .flatMap(branchHandler::saveBranch)
                .flatMap(branch -> ServerResponse.ok().bodyValue(branch));
    }

    public Mono<ServerResponse> updateBranchName(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        String name = serverRequest.queryParam("name").orElseThrow(
                () -> new IllegalArgumentException(ExceptionResponse.NAME_REQUIRED.getMessage())
        );
        return branchHandler.updateBranchName(id, name)
                .flatMap(branch -> ServerResponse.ok().bodyValue(branch));
    }
}
