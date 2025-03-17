package com.nequi.franchisesapi.infraestructure.out.persistence.adapter;

import com.nequi.franchisesapi.domain.model.Branch;
import com.nequi.franchisesapi.domain.spi.IBranchPersistencePort;
import com.nequi.franchisesapi.infraestructure.exception.NoDataFoundException;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IBranchEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IBranchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchPersistenceAdapter implements IBranchPersistencePort {

    private final IBranchRepository branchRepository;
    private final IBranchEntityMapper branchEntityMapper;

    @Override
    public Mono<Branch> saveBranch(Branch branch) {
        return branchRepository.save(branchEntityMapper.toEntity(branch))
                .map(branchEntityMapper::toModel);
    }

    @Override
    public Mono<Branch> findBranchById(Long id) {
        return branchRepository.findById(id)
                .map(branchEntityMapper::toModel)
                .switchIfEmpty(Mono.error(new NoDataFoundException()));
    }

    @Override
    public Mono<Branch> updateBranchName(Long id, String name) {
        return branchRepository.updateName(id, name)
                .then(branchRepository.findById(id))
                .map(branchEntityMapper::toModel)
                .switchIfEmpty(Mono.error(new NoDataFoundException()));
    }
}
