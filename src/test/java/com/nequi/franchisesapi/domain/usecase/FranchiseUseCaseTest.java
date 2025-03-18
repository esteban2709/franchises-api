package com.nequi.franchisesapi.domain.usecase;

import com.nequi.franchisesapi.domain.model.Franchise;
import com.nequi.franchisesapi.domain.spi.IFranchisePersistencePort;
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
class FranchiseUseCaseTest {

    @Mock
    private IFranchisePersistencePort franchisePersistencePort;

    @InjectMocks
    private FranchiseUseCase franchiseUseCase;

    private Franchise franchise;

    @BeforeEach
    void setUp() {
        franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Franquicia Test");
    }

    @Test
    void saveFranchise_shouldSaveFranchiseSuccessfully() {
        when(franchisePersistencePort.saveFranchise(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseUseCase.saveFranchise(franchise))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchisePersistencePort, times(1)).saveFranchise(franchise);
    }

    @Test
    void findFranchiseById_whenFranchiseExists_shouldReturnFranchise() {
        when(franchisePersistencePort.findFranchiseById(anyLong()))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseUseCase.findFranchiseById(1L))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchisePersistencePort, times(1)).findFranchiseById(1L);
    }

    @Test
    void findFranchiseById_whenFranchiseDoesNotExist_shouldReturnEmptyMono() {
        when(franchisePersistencePort.findFranchiseById(anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier.create(franchiseUseCase.findFranchiseById(999L))
                .verifyComplete();

        verify(franchisePersistencePort, times(1)).findFranchiseById(999L);
    }

    @Test
    void updateFranchiseName_whenFranchiseExists_shouldUpdateSuccessfully() {
        Franchise updatedFranchise = new Franchise();
        updatedFranchise.setId(1L);
        updatedFranchise.setName("Franquicia Actualizada");

        when(franchisePersistencePort.updateFranchiseName(anyLong(), anyString()))
                .thenReturn(Mono.just(updatedFranchise));

        StepVerifier.create(franchiseUseCase.updateFranchiseName(1L, "Franquicia Actualizada"))
                .expectNextMatches(result -> {
                    // Validar todos los campos
                    return result.getId().equals(1L) &&
                            result.getName().equals("Franquicia Actualizada");
                })
                .verifyComplete();

        verify(franchisePersistencePort, times(1)).updateFranchiseName(1L, "Franquicia Actualizada");
    }

    @Test
    void updateFranchiseName_whenFranchiseDoesNotExist_shouldReturnEmptyMono() {
        when(franchisePersistencePort.updateFranchiseName(anyLong(), anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(franchiseUseCase.updateFranchiseName(999L, "Nombre Inexistente"))
                .verifyComplete();

        verify(franchisePersistencePort, times(1)).updateFranchiseName(999L, "Nombre Inexistente");
    }
}