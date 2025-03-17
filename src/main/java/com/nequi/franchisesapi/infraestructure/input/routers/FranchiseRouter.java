package com.nequi.franchisesapi.infraestructure.input.routers;

import com.nequi.franchisesapi.application.dto.request.FranchiseRequestDto;
import com.nequi.franchisesapi.domain.model.Franchise;
import com.nequi.franchisesapi.infraestructure.input.handler.FranchiseHandlerImpl;
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
public class FranchiseRouter {

    private static final String FRANCHISE_PATH = "/franchise";

    @Bean
    @RouterOperation(
            path = FRANCHISE_PATH,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = FranchiseHandlerImpl.class,
            beanMethod = "createFranchise",
            operation = @Operation(
                    operationId = "createFranchise",
                    summary = "Crea una nueva franquicia",
                    description = "Endpoint para registrar una nueva franquicia en el sistema",
                    requestBody = @RequestBody(
                            required = true,
                            content = @Content(schema = @Schema(implementation = FranchiseRequestDto.class))
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Franquicia creada exitosamente",
                                    content = @Content(schema = @Schema(implementation = Franchise.class))
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
    public RouterFunction<ServerResponse> franchiseRoutes(FranchiseHandlerImpl franchiseHandlerImpl) {
        return route()
                .POST(FRANCHISE_PATH, franchiseHandlerImpl::createFranchise)
                .PATCH(FRANCHISE_PATH + "/{id}", franchiseHandlerImpl::updateFranchiseName)
//                .DELETE(FRANCHISE_PATH+"/{id}", franchiseHandler::deleteFranchise)
                .build();
    }
}
