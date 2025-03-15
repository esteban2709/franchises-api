package com.nequi.franchisesapi.domain.usecase;

import com.nequi.franchisesapi.domain.api.IFranchiseServicePort;
import com.nequi.franchisesapi.domain.model.Franchise;
import com.nequi.franchisesapi.domain.spi.IFranchisePersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase implements IFranchiseServicePort {

    private final IFranchisePersistencePort franchisePersistencePort;

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return franchisePersistencePort.saveFranchise(franchise);
    }

    @Override
    public Mono<Franchise> findFranchiseById(Long id) {
        return franchisePersistencePort.findFranchiseById(id);
    }

    @Override
    public Mono<Franchise> updateFranchiseName(Long id, String name) {
        return franchisePersistencePort.updateFranchiseName(id, name);
    }
}
