package com.nequi.franchisesapi.infraestructure.input.routers;

import com.nequi.franchisesapi.application.dto.request.BranchRequestDto;
import com.nequi.franchisesapi.domain.model.Branch;
import com.nequi.franchisesapi.infraestructure.input.handler.BranchHandlerImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BranchRouter {

    private static final String BRANCHES_PATH = "/branch";

    @Bean
    @RouterOperation(
            path = "/" + BRANCHES_PATH,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = BranchHandlerImpl.class,
            beanMethod = "createBranch",
            operation = @Operation(
                    operationId = "createBranch",
                    summary = "Crea una nueva franquicia",
                    description = "Endpoint para registrar una nueva franquicia en el sistema",
                    requestBody = @RequestBody(
                            required = true,
                            content = @Content(schema = @Schema(implementation = BranchRequestDto.class))
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Franquicia creada exitosamente",
                                    content = @Content(schema = @Schema(implementation = Branch.class))
                            ),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = "Datos de entrada inválidos"
                            ),
                            @ApiResponse(
                                    responseCode = "500",
                                    description = "Error interno del servidor"
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> branchRoutes(BranchHandlerImpl branchHandler) {
        return route()
                .POST(BRANCHES_PATH, branchHandler::createBranch)
//                .GET(BRANCHES_PATH + "/{id}", branchHandler::getBranchById)
//                .PUT(BRANCHES_PATH + "/{id}", branchHandler::updateBranch)
                .build();
    }
}
