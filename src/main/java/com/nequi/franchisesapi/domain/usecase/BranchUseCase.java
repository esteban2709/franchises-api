package com.nequi.franchisesapi.domain.usecase;

import com.nequi.franchisesapi.domain.api.IBranchServicePort;
import com.nequi.franchisesapi.domain.model.Branch;
import com.nequi.franchisesapi.domain.spi.IBranchPersistencePort;
import com.nequi.franchisesapi.domain.utils.validations.Validations;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase implements IBranchServicePort {

    private final IBranchPersistencePort branchPersistencePort;
    private final Validations validations;

    @Override
    public Mono<Branch> saveBranch(Branch branch) {
        return validations.existFranchise(branch.getFranchiseId())
        .then(Mono.defer(() ->  branchPersistencePort.saveBranch(branch)));
    }

    @Override
    public Mono<Branch> findBranchById(Long id) {
        return branchPersistencePort.findBranchById(id);
    }

    @Override
    public Mono<Branch> updateBranchName(Long id, String name) {
        return validations.existBranch(id)
                .then(Mono.defer(() -> branchPersistencePort.updateBranchName(id, name)));
    }
}
