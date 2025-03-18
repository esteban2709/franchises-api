package com.nequi.franchisesapi.application.handler.impl;

import com.nequi.franchisesapi.application.dto.request.FranchiseRequestDto;
import com.nequi.franchisesapi.application.mapper.IFranchiseRequestMapper;
import com.nequi.franchisesapi.domain.api.IFranchiseServicePort;
import com.nequi.franchisesapi.domain.model.Franchise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseHandlerTest {

    @Mock
    private IFranchiseServicePort franchiseServicePort;

    @Mock
    private IFranchiseRequestMapper franchiseRequestMapper;

    @InjectMocks
    private FranchiseHandler franchiseHandler;

    private FranchiseRequestDto franchiseRequestDto;
    private Franchise franchise;

    @BeforeEach
    void setUp() {
        // Inicializar objetos para pruebas
        franchiseRequestDto = new FranchiseRequestDto();
        franchiseRequestDto.setName("Franquicia Test");

        franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Franquicia Test");
    }

    @Test
    void saveFranchise_shouldReturnSavedFranchise() {
        when(franchiseRequestMapper.toModel(any(FranchiseRequestDto.class))).thenReturn(franchise);
        when(franchiseServicePort.saveFranchise(any(Franchise.class))).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseHandler.saveFranchise(franchiseRequestDto))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseRequestMapper).toModel(franchiseRequestDto);
        verify(franchiseServicePort).saveFranchise(franchise);
    }

    @Test
    void findFranchiseById_shouldReturnFranchise() {
        when(franchiseServicePort.findFranchiseById(anyLong())).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseHandler.findFranchiseById(1L))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseServicePort).findFranchiseById(1L);
    }

    @Test
    void updateFranchiseName_shouldReturnUpdatedFranchise() {
        when(franchiseServicePort.updateFranchiseName(anyLong(), anyString())).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseHandler.updateFranchiseName(1L, "Franquicia Actualizada"))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseServicePort).updateFranchiseName(1L, "Franquicia Actualizada");
    }
}