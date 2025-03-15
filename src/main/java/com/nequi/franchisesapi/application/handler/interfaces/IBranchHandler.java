package com.nequi.franchisesapi.application.handler.interfaces;

import com.nequi.franchisesapi.application.dto.request.BranchRequestDto;
import com.nequi.franchisesapi.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface IBranchHandler {

    Mono<Branch> saveBranch(BranchRequestDto branch);
    Mono<Branch> findBranchById(Long id);
    Mono<Branch> updateBranchName(Long id, String name);
}
