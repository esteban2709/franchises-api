package com.nequi.franchisesapi.infraestructure.input.routers;

import com.nequi.franchisesapi.application.dto.request.BranchRequestDto;
import com.nequi.franchisesapi.domain.model.Branch;
import com.nequi.franchisesapi.infraestructure.input.handler.BranchHandlerImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
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
    @RouterOperations({
            @RouterOperation(
                    path = BRANCHES_PATH,
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
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = BRANCHES_PATH + "/{id}",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    method = RequestMethod.PATCH,
                    beanClass = BranchHandlerImpl.class,
                    beanMethod = "updateBranchName",
                    operation = @Operation(
                            operationId = "updateBranchName",
                            summary = "Actualiza el nombre de una sucursal",
                            description = "Endpoint para actualizar el nombre de una sucursal existente en el sistema",
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            description = "ID de la sucursal a actualizar",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            schema = @Schema(type = "integer", format = "int64")
                                    ),
                                    @Parameter(
                                            name = "name",
                                            description = "Nuevo nombre para la sucursal",
                                            in = ParameterIn.QUERY,
                                            required = true,
                                            schema = @Schema(type = "string")
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Sucursal actualizada exitosamente",
                                            content = @Content(schema = @Schema(implementation = Branch.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Datos de entrada inválidos"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> branchRoutes(BranchHandlerImpl branchHandler) {
        return route()
                .POST(BRANCHES_PATH, branchHandler::createBranch)
                .PATCH(BRANCHES_PATH + "/{id}", branchHandler::updateBranchName)
                .build();
    }
}
