package com.nequi.franchisesapi.domain.usecase;

import com.nequi.franchisesapi.domain.api.IBranchServicePort;
import com.nequi.franchisesapi.domain.model.Branch;
import com.nequi.franchisesapi.domain.spi.IBranchPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase implements IBranchServicePort {

    private final IBranchPersistencePort branchPersistencePort;

    @Override
    public Mono<Branch> saveBranch(Branch branch) {
        return branchPersistencePort.saveBranch(branch);
    }

    @Override
    public Mono<Branch> findBranchById(Long id) {
        return branchPersistencePort.findBranchById(id);
    }

    @Override
    public Mono<Branch> updateBranchName(Long id, String name) {
        return branchPersistencePort.updateBranchName(id, name);
    }
}
