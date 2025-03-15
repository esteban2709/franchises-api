package com.nequi.franchisesapi.domain.api;

import com.nequi.franchisesapi.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface IBranchServicePort {
    Mono<Branch> saveBranch(Branch branch);

    Mono<Branch> findBranchById(Long id);

    Mono<Branch> updateBranchName(Long id, String name);
}
