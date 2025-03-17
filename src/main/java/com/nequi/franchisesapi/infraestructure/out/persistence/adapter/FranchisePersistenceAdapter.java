package com.nequi.franchisesapi.infraestructure.out.persistence.adapter;

import com.nequi.franchisesapi.domain.model.Franchise;
import com.nequi.franchisesapi.domain.spi.IFranchisePersistencePort;
import com.nequi.franchisesapi.infraestructure.exception.NoDataFoundException;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.FranchiseEntity;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IFranchiseEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IFranchiseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class FranchisePersistenceAdapter implements IFranchisePersistencePort {

    private final IFranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper franchiseEntityMapper;

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return franchiseRepository.save(franchiseEntityMapper.toEntity(franchise))
                .map(franchiseEntityMapper::toModel);
    }

    @Override
    public Mono<Franchise> findFranchiseById(Long id) {
        return franchiseRepository.findById(id)
                .map(franchiseEntityMapper::toModel)
                .switchIfEmpty(Mono.error(new NoDataFoundException()));
    }

    @Override
    public Mono<Franchise> updateFranchiseName(Long id, String name) {
        return franchiseRepository.updateName(id, name)
                .then(franchiseRepository.findById(id))
                .map(franchiseEntityMapper::toModel)
                .switchIfEmpty(Mono.error(new NoDataFoundException()));
    }
}
