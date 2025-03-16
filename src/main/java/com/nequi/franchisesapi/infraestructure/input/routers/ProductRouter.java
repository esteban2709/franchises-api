package com.nequi.franchisesapi.infraestructure.input.routers;

import com.nequi.franchisesapi.application.dto.request.ProductRequestDto;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.infraestructure.input.handler.ProductHandlerImpl;
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
public class ProductRouter {

    private static final String PRODUCTS_PATH = "/product";

    @Bean
    @RouterOperation(
            path = PRODUCTS_PATH,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = ProductHandlerImpl.class,
            beanMethod = "createProduct",
            operation = @Operation(
                    operationId = "createProduct",
                    summary = "Crea una nueva franquicia",
                    description = "Endpoint para registrar una nueva franquicia en el sistema",
                    requestBody = @RequestBody(
                            required = true,
                            content = @Content(schema = @Schema(implementation = ProductRequestDto.class))
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Franquicia creada exitosamente",
                                    content = @Content(schema = @Schema(implementation = Product.class))
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
    public RouterFunction<ServerResponse> productRoutes(ProductHandlerImpl productHandler) {
        return route()
                .POST(PRODUCTS_PATH, productHandler::createProduct)
//                .GET(PRODUCTS_PATH + "/{id}", productHandler::getProductById)
//                .PUT(PRODUCTS_PATH + "/{id}", productHandler::updateProduct)
//                .DELETE(PRODUCTS_PATH + "/{id}", productHandler::deleteProduct)
                .build();
    }
}
