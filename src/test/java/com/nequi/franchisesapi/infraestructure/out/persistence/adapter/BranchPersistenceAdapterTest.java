package com.nequi.franchisesapi.infraestructure.out.persistence.adapter;

import com.nequi.franchisesapi.domain.model.Branch;
import com.nequi.franchisesapi.infraestructure.exception.NoDataFoundException;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.BranchEntity;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IBranchEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IBranchRepository;
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
class BranchPersistenceAdapterTest {

    @Mock
    private IBranchRepository branchRepository;

    @Mock
    private IBranchEntityMapper branchEntityMapper;

    @InjectMocks
    private BranchPersistenceAdapter branchPersistenceAdapter;

    private Branch branch;
    private BranchEntity branchEntity;

    @BeforeEach
    void setUp() {
        // Configurar el objeto Branch
        branch = new Branch();
        branch.setId(1L);
        branch.setName("Sucursal Test");
        branch.setFranchiseId(2L);

        // Configurar el objeto BranchEntity
        branchEntity = new BranchEntity();
        branchEntity.setId(1L);
        branchEntity.setName("Sucursal Test");
        branchEntity.setFranchiseId(2L);
    }

    @Test
    void saveBranch_shouldSaveBranchAndReturnSavedBranch() {
        when(branchEntityMapper.toEntity(any(Branch.class))).thenReturn(branchEntity);
        when(branchRepository.save(any(BranchEntity.class))).thenReturn(Mono.just(branchEntity));
        when(branchEntityMapper.toModel(any(BranchEntity.class))).thenReturn(branch);

        Mono<Branch> result = branchPersistenceAdapter.saveBranch(branch);

        StepVerifier.create(result)
                .expectNextMatches(savedBranch -> {
                    assertEquals(branch.getId(), savedBranch.getId(), "El ID debe coincidir");
                    assertEquals(branch.getName(), savedBranch.getName(), "El nombre debe coincidir");
                    assertEquals(branch.getFranchiseId(), savedBranch.getFranchiseId(), "El ID de la franquicia debe coincidir");
                    return true;
                })
                .verifyComplete();

        verify(branchEntityMapper).toEntity(branch);
        verify(branchRepository).save(branchEntity);
        verify(branchEntityMapper).toModel(branchEntity);
    }

    @Test
    void findBranchById_whenBranchExists_shouldReturnBranch() {
        when(branchRepository.findById(anyLong())).thenReturn(Mono.just(branchEntity));
        when(branchEntityMapper.toModel(any(BranchEntity.class))).thenReturn(branch);

        Mono<Branch> result = branchPersistenceAdapter.findBranchById(1L);

        StepVerifier.create(result)
                .expectNextMatches(foundBranch -> {
                    assertEquals(branch.getId(), foundBranch.getId(), "El ID debe coincidir");
                    assertEquals(branch.getName(), foundBranch.getName(), "El nombre debe coincidir");
                    assertEquals(branch.getFranchiseId(), foundBranch.getFranchiseId(), "El ID de la franquicia debe coincidir");
                    return true;
                })
                .verifyComplete();

        verify(branchRepository).findById(1L);
        verify(branchEntityMapper).toModel(branchEntity);
    }

    @Test
    void findBranchById_whenBranchDoesNotExist_shouldReturnEmptyMono() {
        when(branchRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<Branch> result = branchPersistenceAdapter.findBranchById(1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(branchRepository).findById(1L);
        verify(branchEntityMapper, never()).toModel(any(BranchEntity.class));
    }

    @Test
    void updateBranchName_whenBranchExists_shouldUpdateAndReturnBranch() {
        when(branchRepository.updateName(anyLong(), anyString())).thenReturn(Mono.empty());
        when(branchRepository.findById(anyLong())).thenReturn(Mono.just(branchEntity));
        when(branchEntityMapper.toModel(any(BranchEntity.class))).thenReturn(branch);

        Mono<Branch> result = branchPersistenceAdapter.updateBranchName(1L, "Nuevo Nombre");

        StepVerifier.create(result)
                .expectNextMatches(updatedBranch -> {
                    assertEquals(branch.getId(), updatedBranch.getId(), "El ID debe coincidir");
                    assertEquals(branch.getName(), updatedBranch.getName(), "El nombre debe coincidir");
                    assertEquals(branch.getFranchiseId(), updatedBranch.getFranchiseId(), "El ID de la franquicia debe coincidir");
                    return true;
                })
                .verifyComplete();

        verify(branchRepository).updateName(1L, "Nuevo Nombre");
        verify(branchRepository).findById(1L);
        verify(branchEntityMapper).toModel(branchEntity);
    }

    @Test
    void updateBranchName_whenBranchDoesNotExist_shouldThrowNoDataFoundException() {
        when(branchRepository.updateName(anyLong(), anyString())).thenReturn(Mono.empty());
        when(branchRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<Branch> result = branchPersistenceAdapter.updateBranchName(1L, "Nuevo Nombre");

        StepVerifier.create(result)
                .expectError(NoDataFoundException.class)
                .verify();

        verify(branchRepository).updateName(1L, "Nuevo Nombre");
        verify(branchRepository).findById(1L);
        verify(branchEntityMapper, never()).toModel(any(BranchEntity.class));
    }
}