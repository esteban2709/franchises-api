package com.nequi.franchisesapi.application.handler.impl;

import com.nequi.franchisesapi.application.dto.request.BranchRequestDto;
import com.nequi.franchisesapi.application.mapper.IBranchRequestMapper;
import com.nequi.franchisesapi.domain.api.IBranchServicePort;
import com.nequi.franchisesapi.domain.model.Branch;
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
class BranchHandlerTest {

    @Mock
    private IBranchServicePort branchServicePort;

    @Mock
    private IBranchRequestMapper branchRequestMapper;

    @InjectMocks
    private BranchHandler branchHandler;

    private BranchRequestDto branchRequestDto;
    private Branch branch;

    @BeforeEach
    void setUp() {
        // Inicializar objetos para pruebas
        branchRequestDto = new BranchRequestDto();
        branchRequestDto.setName("Sucursal Test");
        branchRequestDto.setFranchiseId(1L);

        branch = new Branch();
        branch.setId(1L);
        branch.setName("Sucursal Test");
        branch.setFranchiseId(1L);
    }

    @Test
    void saveBranch_shouldReturnSavedBranch() {
        when(branchRequestMapper.toModel(any(BranchRequestDto.class))).thenReturn(branch);
        when(branchServicePort.saveBranch(any(Branch.class))).thenReturn(Mono.just(branch));

        StepVerifier.create(branchHandler.saveBranch(branchRequestDto))
                .expectNext(branch)
                .verifyComplete();

        verify(branchRequestMapper).toModel(branchRequestDto);
        verify(branchServicePort).saveBranch(branch);
    }

    @Test
    void findBranchById_shouldReturnBranch() {
        when(branchServicePort.findBranchById(anyLong())).thenReturn(Mono.just(branch));

        StepVerifier.create(branchHandler.findBranchById(1L))
                .expectNext(branch)
                .verifyComplete();

        verify(branchServicePort).findBranchById(1L);
    }

    @Test
    void updateBranchName_shouldReturnUpdatedBranch() {
        when(branchServicePort.updateBranchName(anyLong(), anyString())).thenReturn(Mono.just(branch));

        StepVerifier.create(branchHandler.updateBranchName(1L, "Sucursal Actualizada"))
                .expectNext(branch)
                .verifyComplete();

        verify(branchServicePort).updateBranchName(1L, "Sucursal Actualizada");
    }
}