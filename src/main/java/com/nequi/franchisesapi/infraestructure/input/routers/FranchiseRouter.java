package com.nequi.franchisesapi.infraestructure.input.routers;

import com.nequi.franchisesapi.infraestructure.input.handler.FranchiseHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class FranchiseRouter {

    private static final String FRANCHISE_PATH = "franchise";

    @Bean
    public RouterFunction<ServerResponse> franchiseRoutes(FranchiseHandlerImpl franchiseHandler) {
        return route()
                .POST("/"+FRANCHISE_PATH, franchiseHandler::createFranchise)
//                .DELETE("/"+FRANCHISE_PATH+"/{id}", franchiseHandler::deleteFranchise)
                .build();
    }
}
