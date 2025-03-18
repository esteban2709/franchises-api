package com.nequi.franchisesapi.infraestructure.input.routers;

import com.nequi.franchisesapi.application.dto.request.ProductRequestDto;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.utils.ProductStockByBranch;
import com.nequi.franchisesapi.infraestructure.input.handler.ProductHandlerImpl;
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
public class ProductRouter {

    private static final String PRODUCTS_PATH = "/product";

    @Bean
    @RouterOperations({
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
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = PRODUCTS_PATH + "/update-stock",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    method = RequestMethod.PATCH,
                    beanClass = ProductHandlerImpl.class,
                    beanMethod = "updateProductStock",
                    operation = @Operation(
                            operationId = "updateProductStock",
                            summary = "Actualiza el stock de un producto",
                            description = "Endpoint para actualizar el stock de un producto en una sucursal específica",
                            parameters = {
                                    @Parameter(
                                            name = "productId",
                                            description = "ID del producto",
                                            in = ParameterIn.QUERY,
                                            required = true,
                                            schema = @Schema(type = "integer", format = "int64")
                                    ),
                                    @Parameter(
                                            name = "branchId",
                                            description = "ID de la sucursal",
                                            in = ParameterIn.QUERY,
                                            required = true,
                                            schema = @Schema(type = "integer", format = "int64")
                                    ),
                                    @Parameter(
                                            name = "stock",
                                            description = "Nueva cantidad de stock",
                                            in = ParameterIn.QUERY,
                                            required = true,
                                            schema = @Schema(type = "integer")
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Stock actualizado exitosamente"
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Datos de entrada inválidos"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = PRODUCTS_PATH + "/top-stock/{id}",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    method = RequestMethod.GET,
                    beanClass = ProductHandlerImpl.class,
                    beanMethod = "getTopStockProductsByBranchByFranchiseId",
                    operation = @Operation(
                            operationId = "getTopStockProductsByBranchByFranchiseId",
                            summary = "Obtiene productos con mayor stock por sucursal de una franquicia",
                            description = "Endpoint para obtener los productos con mayor stock por sucursal en una franquicia específica",
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            description = "ID de la franquicia",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            schema = @Schema(type = "integer", format = "int64")
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Consulta exitosa",
                                            content = @Content(schema = @Schema(implementation = ProductStockByBranch.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Franquicia no encontrada"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = PRODUCTS_PATH + "/{id}",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    method = RequestMethod.PATCH,
                    beanClass = ProductHandlerImpl.class,
                    beanMethod = "updateProductName",
                    operation = @Operation(
                            operationId = "updateProductName",
                            summary = "Actualiza el nombre de un producto",
                            description = "Endpoint para actualizar el nombre de un producto existente",
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            description = "ID del producto a actualizar",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            schema = @Schema(type = "integer", format = "int64")
                                    ),
                                    @Parameter(
                                            name = "name",
                                            description = "Nuevo nombre para el producto",
                                            in = ParameterIn.QUERY,
                                            required = true,
                                            schema = @Schema(type = "string")
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Producto actualizado exitosamente",
                                            content = @Content(schema = @Schema(implementation = Product.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Datos de entrada inválidos"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Producto no encontrado"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = PRODUCTS_PATH + "/{id}",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    method = RequestMethod.DELETE,
                    beanClass = ProductHandlerImpl.class,
                    beanMethod = "deleteProduct",
                    operation = @Operation(
                            operationId = "deleteProduct",
                            summary = "Elimina un producto",
                            description = "Endpoint para eliminar un producto y sus relaciones con sucursales",
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            description = "ID del producto a eliminar",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            schema = @Schema(type = "integer", format = "int64")
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "Producto eliminado exitosamente"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Producto no encontrado"
                                    )
                            }
                    )
            )

    })

    public RouterFunction<ServerResponse> productRoutes(ProductHandlerImpl productHandler) {
        return route()
                .POST(PRODUCTS_PATH, productHandler::createProduct)
                .PATCH(PRODUCTS_PATH + "/update-stock", productHandler::updateProductStock)
                .GET(PRODUCTS_PATH + "/top-stock/{id}", productHandler::getTopStockProductsByBranchByFranchiseId)
                .PATCH(PRODUCTS_PATH+"/{id}", productHandler::updateProductName)
                .DELETE(PRODUCTS_PATH + "/{id}", productHandler::deleteProduct)
                .build();
    }
}
