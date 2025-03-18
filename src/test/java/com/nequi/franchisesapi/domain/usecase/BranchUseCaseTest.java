package com.nequi.franchisesapi.domain.usecase;

import com.nequi.franchisesapi.domain.exception.CustomException;
import com.nequi.franchisesapi.domain.exception.ExceptionMessage;
import com.nequi.franchisesapi.domain.model.Branch;
import com.nequi.franchisesapi.domain.spi.IBranchPersistencePort;
import com.nequi.franchisesapi.domain.utils.validations.Validations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchUseCaseTest {

    @Mock
    private IBranchPersistencePort branchPersistencePort;

    @Mock
    private Validations validations;

    @InjectMocks
    private BranchUseCase branchUseCase;

    private Branch branch;

    @BeforeEach
    void setUp() {
        branch = new Branch();
        branch.setId(1L);
        branch.setName("Sucursal Test");
        branch.setFranchiseId(2L);
    }

    @Test
    void saveBranch_whenFranchiseExists_shouldSaveBranch() {
        when(validations.existFranchise(anyLong())).thenReturn(Mono.just(true));
        when(branchPersistencePort.saveBranch(any(Branch.class))).thenReturn(Mono.just(branch));

        StepVerifier.create(branchUseCase.saveBranch(branch))
                .expectNextMatches(savedBranch -> {
                    // Validamos todos los campos
                    assertEquals(1L, savedBranch.getId());
                    assertEquals("Sucursal Test", savedBranch.getName());
                    assertEquals(2L, savedBranch.getFranchiseId());
                    return true;
                })
                .verifyComplete();

        // Verificamos que se llamaron los métodos correctos
        verify(validations).existFranchise(2L);
        verify(branchPersistencePort).saveBranch(branch);
    }

    @Test
    void saveBranch_whenFranchiseDoesNotExist_shouldReturnError() {
        // Configura el mock para capturar la pila de llamadas
        CustomException exception = new CustomException(ExceptionMessage.FRANCHISE_NOT_FOUND.getMessage());
        when(validations.existFranchise(anyLong()))
                .thenReturn(Mono.error(exception));

        // Act & Assert con más detalles
        StepVerifier.create(branchUseCase.saveBranch(branch))
                .expectErrorSatisfies(error -> {
                    assertInstanceOf(CustomException.class, error);
                })
                .verify();

        verify(validations).existFranchise(2L);
        verify(branchPersistencePort, never()).saveBranch(any(Branch.class));
    }

    @Test
    void findBranchById_whenBranchExists_shouldReturnBranch() {
        when(branchPersistencePort.findBranchById(anyLong())).thenReturn(Mono.just(branch));

        StepVerifier.create(branchUseCase.findBranchById(1L))
                .expectNextMatches(foundBranch -> {
                    assertEquals(1L, foundBranch.getId());
                    assertEquals("Sucursal Test", foundBranch.getName());
                    assertEquals(2L, foundBranch.getFranchiseId());
                    return true;
                })
                .verifyComplete();

        verify(branchPersistencePort).findBranchById(1L);
    }

    @Test
    void findBranchById_whenBranchDoesNotExist_shouldReturnEmpty() {
        when(branchPersistencePort.findBranchById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.findBranchById(1L))
                .expectNextCount(0)
                .verifyComplete();

        verify(branchPersistencePort).findBranchById(1L);
    }

    @Test
    void updateBranchName_whenBranchExists_shouldUpdateName() {
        // Configura el mock para el caso de éxito
        when(validations.existBranch(anyLong())).thenReturn(Mono.just(true));
        // También configura el comportamiento de updateBranchName si es necesario
        when(branchPersistencePort.updateBranchName(anyLong(), anyString()))
                .thenReturn(Mono.just(new Branch(/* datos apropiados */)));

        StepVerifier.create(branchUseCase.updateBranchName(1L, "Nuevo Nombre"))
                .expectNextCount(1)
                .verifyComplete();

        verify(validations).existBranch(1L);
        verify(branchPersistencePort).updateBranchName(1L, "Nuevo Nombre");
    }

    @Test
    void updateBranchName_whenBranchDoesNotExist_shouldReturnError() {
        // Configura el mock de existBranch, no existFranchise
        when(validations.existBranch(anyLong()))
                .thenReturn(Mono.error(new CustomException(ExceptionMessage.BRANCH_NOT_FOUND.getMessage())));

        StepVerifier.create(branchUseCase.updateBranchName(1L, "Nuevo Nombre"))
                .expectError(CustomException.class)
                .verify();

        verify(validations).existBranch(1L);
        verify(branchPersistencePort, never()).updateBranchName(anyLong(), anyString());
    }
}