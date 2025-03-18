package com.nequi.franchisesapi.infraestructure.out.persistence.adapter;

import com.nequi.franchisesapi.domain.model.Franchise;
import com.nequi.franchisesapi.infraestructure.exception.NoDataFoundException;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.FranchiseEntity;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IFranchiseEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IFranchiseRepository;
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
class FranchisePersistenceAdapterTest {

    @Mock
    private IFranchiseRepository franchiseRepository;

    @Mock
    private IFranchiseEntityMapper franchiseEntityMapper;

    @InjectMocks
    private FranchisePersistenceAdapter franchisePersistenceAdapter;

    private Franchise franchise;
    private FranchiseEntity franchiseEntity;

    @BeforeEach
    void setUp() {
        // Configurar objeto Franchise
        franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Franquicia Test");

        // Configurar objeto FranchiseEntity
        franchiseEntity = new FranchiseEntity();
        franchiseEntity.setId(1L);
        franchiseEntity.setName("Franquicia Test");
    }

    @Test
    void saveFranchise_shouldSaveFranchiseAndReturnSavedFranchise() {
        when(franchiseEntityMapper.toEntity(any(Franchise.class))).thenReturn(franchiseEntity);
        when(franchiseRepository.save(any(FranchiseEntity.class))).thenReturn(Mono.just(franchiseEntity));
        when(franchiseEntityMapper.toModel(any(FranchiseEntity.class))).thenReturn(franchise);

        Mono<Franchise> result = franchisePersistenceAdapter.saveFranchise(franchise);

        StepVerifier.create(result)
                .expectNextMatches(savedFranchise -> {
                    assertEquals(franchise.getId(), savedFranchise.getId(), "El ID debe coincidir");
                    assertEquals(franchise.getName(), savedFranchise.getName(), "El nombre debe coincidir");
                    return true;
                })
                .verifyComplete();

        // Verificar que los métodos fueron llamados con los parámetros correctos
        verify(franchiseEntityMapper).toEntity(franchise);
        verify(franchiseRepository).save(franchiseEntity);
        verify(franchiseEntityMapper).toModel(franchiseEntity);
    }

    @Test
    void findFranchiseById_shouldReturnFranchiseWhenFound() {
        when(franchiseRepository.findById(anyLong())).thenReturn(Mono.just(franchiseEntity));
        when(franchiseEntityMapper.toModel(any(FranchiseEntity.class))).thenReturn(franchise);

        Mono<Franchise> result = franchisePersistenceAdapter.findFranchiseById(1L);

        StepVerifier.create(result)
                .expectNextMatches(foundFranchise -> {
                    assertEquals(franchise.getId(), foundFranchise.getId(), "El ID debe coincidir");
                    assertEquals(franchise.getName(), foundFranchise.getName(), "El nombre debe coincidir");
                    return true;
                })
                .verifyComplete();

        // Verificar que los métodos fueron llamados con los parámetros correctos
        verify(franchiseRepository).findById(1L);
        verify(franchiseEntityMapper).toModel(franchiseEntity);
    }

    @Test
    void findFranchiseById_shouldReturnEmptyMonoWhenNotFound() {
        when(franchiseRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<Franchise> result = franchisePersistenceAdapter.findFranchiseById(1L);

        StepVerifier.create(result)
                .verifyComplete();

        // Verificar que los métodos fueron llamados con los parámetros correctos
        verify(franchiseRepository).findById(1L);
        verify(franchiseEntityMapper, never()).toModel(any());
    }

    @Test
    void updateFranchiseName_shouldUpdateAndReturnUpdatedFranchise() {
        when(franchiseRepository.updateName(anyLong(), anyString())).thenReturn(Mono.just(1));
        when(franchiseRepository.findById(anyLong())).thenReturn(Mono.just(franchiseEntity));
        when(franchiseEntityMapper.toModel(any(FranchiseEntity.class))).thenReturn(franchise);

        Mono<Franchise> result = franchisePersistenceAdapter.updateFranchiseName(1L, "Nuevo Nombre");

        StepVerifier.create(result)
                .expectNextMatches(updatedFranchise -> {
                    assertEquals(franchise.getId(), updatedFranchise.getId(), "El ID debe coincidir");
                    assertEquals(franchise.getName(), updatedFranchise.getName(), "El nombre debe coincidir");
                    return true;
                })
                .verifyComplete();

        // Verificar que los métodos fueron llamados con los parámetros correctos
        verify(franchiseRepository).updateName(1L, "Nuevo Nombre");
        verify(franchiseRepository).findById(1L);
        verify(franchiseEntityMapper).toModel(franchiseEntity);
    }

    @Test
    void updateFranchiseName_shouldThrowExceptionWhenFranchiseNotFound() {
        when(franchiseRepository.updateName(anyLong(), anyString())).thenReturn(Mono.just(1));
        when(franchiseRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<Franchise> result = franchisePersistenceAdapter.updateFranchiseName(1L, "Nuevo Nombre");

        StepVerifier.create(result)
                .expectError(NoDataFoundException.class)
                .verify();

        // Verificar que los métodos fueron llamados con los parámetros correctos
        verify(franchiseRepository).updateName(1L, "Nuevo Nombre");
        verify(franchiseRepository).findById(1L);
        verify(franchiseEntityMapper, never()).toModel(any());
    }
}