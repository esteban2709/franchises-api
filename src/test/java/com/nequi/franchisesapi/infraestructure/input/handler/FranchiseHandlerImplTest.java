package com.nequi.franchisesapi.infraestructure.input.handler;

import com.nequi.franchisesapi.application.dto.request.FranchiseRequestDto;
import com.nequi.franchisesapi.application.handler.interfaces.IFranchiseHandler;
import com.nequi.franchisesapi.domain.model.Franchise;
import com.nequi.franchisesapi.infraestructure.exeptionhandler.ExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FranchiseHandlerImplTest {

    @Mock
    private IFranchiseHandler franchiseHandler;

    @InjectMocks
    private FranchiseHandlerImpl franchiseHandlerImpl;

    private FranchiseRequestDto franchiseRequestDto;
    private Franchise franchise;

    @BeforeEach
    void setUp() {
        franchiseRequestDto = new FranchiseRequestDto();
        franchiseRequestDto.setName("Franquicia Test");

        franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Franquicia Test");
    }

    @Test
    void createFranchise_shouldReturnOkResponse() {
        ServerRequest request = MockServerRequest.builder()
                .body(Mono.just(franchiseRequestDto));

        when(franchiseHandler.saveFranchise(any(FranchiseRequestDto.class)))
                .thenReturn(Mono.just(franchise));

        Mono<ServerResponse> response = franchiseHandlerImpl.createFranchise(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(franchiseHandler).saveFranchise(any(FranchiseRequestDto.class));
    }

    @Test
    void updateFranchiseName_shouldReturnOkResponse() {
        MockServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .queryParam("name", "Nuevo Nombre")
                .build();

        when(franchiseHandler.updateFranchiseName(eq(1L), eq("Nuevo Nombre")))
                .thenReturn(Mono.just(franchise));

        Mono<ServerResponse> response = franchiseHandlerImpl.updateFranchiseName(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(franchiseHandler).updateFranchiseName(1L, "Nuevo Nombre");
    }

    @Test
    void updateFranchiseName_whenNameIsMissing_shouldThrowException() {
        // Crear un ServerRequest mock sin el parámetro nombre
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                // No incluimos el queryParam "name"
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> franchiseHandlerImpl.updateFranchiseName(request).block());

        assertEquals(ExceptionResponse.NAME_REQUIRED.getMessage(), exception.getMessage());

        // No deberías verificar interacciones con franchiseHandler porque el error ocurre antes
        verify(franchiseHandler, never()).updateFranchiseName(any(), any());
    }
}