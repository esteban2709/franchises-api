package com.nequi.franchisesapi.infraestructure.input.handler;

import com.nequi.franchisesapi.application.dto.request.BranchRequestDto;
import com.nequi.franchisesapi.application.handler.interfaces.IBranchHandler;
import com.nequi.franchisesapi.domain.model.Branch;
import com.nequi.franchisesapi.infraestructure.exeptionhandler.ExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchHandlerImplTest {

    @Mock
    private IBranchHandler branchHandler;

    @InjectMocks
    private BranchHandlerImpl branchHandlerImpl;

    private BranchRequestDto branchRequestDto;
    private Branch branch;

    @BeforeEach
    void setUp() {
        // Inicializar objetos para pruebas
        branchRequestDto = new BranchRequestDto();
        branchRequestDto.setName("Test Branch");
        branchRequestDto.setFranchiseId(1L);

        branch = new Branch();
        branch.setId(1L);
        branch.setName("Test Branch");
        branch.setFranchiseId(1L);
    }

    @Test
    void createBranch_shouldReturnOkResponse() {
        ServerRequest request = MockServerRequest.builder()
                .body(Mono.just(branchRequestDto));

        when(branchHandler.saveBranch(any(BranchRequestDto.class)))
                .thenReturn(Mono.just(branch));

        Mono<ServerResponse> response = branchHandlerImpl.createBranch(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> {
                    assertEquals(HttpStatus.OK, serverResponse.statusCode());
                    return true;
                })
                .verifyComplete();

        verify(branchHandler).saveBranch(any(BranchRequestDto.class));
    }

    @Test
    void createBranch_whenBodyIsInvalid_shouldReturnBadRequest() {
        ServerRequest request = MockServerRequest.builder()
                .body(Mono.error(new IllegalArgumentException("Invalid body")));

        Mono<ServerResponse> response = branchHandlerImpl.createBranch(request);

        StepVerifier.create(response)
                .expectError() // Debería fallar ya que el body es inválido
                .verify();
    }

    @Test
    void updateBranchName_shouldReturnOkResponse() {
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .queryParam("name", "Updated Branch Name")
                .build();

        when(branchHandler.updateBranchName(anyLong(), anyString()))
                .thenReturn(Mono.just(branch));

        Mono<ServerResponse> response = branchHandlerImpl.updateBranchName(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> {
                    assertEquals(HttpStatus.OK, serverResponse.statusCode());
                    return true;
                })
                .verifyComplete();

        verify(branchHandler).updateBranchName(1L, "Updated Branch Name");
    }

    @Test
    void updateBranchName_whenNameIsMissing_shouldThrowException() {
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> branchHandlerImpl.updateBranchName(request).block());

        assertEquals(ExceptionResponse.NAME_REQUIRED.getMessage(), exception.getMessage());
        verify(branchHandler, never()).updateBranchName(anyLong(), anyString());
    }

    @Test
    void updateBranchName_whenInvalidId_shouldHandleError() {
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "abc")
                .queryParam("name", "Updated Branch Name")
                .build();

        NumberFormatException exception = assertThrows(NumberFormatException.class,
                () -> branchHandlerImpl.updateBranchName(request).block());

        assertEquals("For input string: \"abc\"", exception.getMessage());
        verify(branchHandler, never()).updateBranchName(anyLong(), anyString());
    }
}