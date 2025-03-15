package com.nequi.franchisesapi.application.handler.impl;

import com.nequi.franchisesapi.application.dto.request.BranchRequestDto;
import com.nequi.franchisesapi.application.handler.interfaces.IBranchHandler;
import com.nequi.franchisesapi.application.mapper.IBranchRequestMapper;
import com.nequi.franchisesapi.domain.api.IBranchServicePort;
import com.nequi.franchisesapi.domain.model.Branch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Transactional
public class BranchHandler implements IBranchHandler {

    private final IBranchServicePort branchServicePort;
    private final IBranchRequestMapper branchRequestMapper;

    @Override
    public Mono<Branch> saveBranch(BranchRequestDto branch) {
        return branchServicePort.saveBranch(branchRequestMapper.toModel(branch));
    }

    @Override
    public Mono<Branch> findBranchById(Long id) {
        return branchServicePort.findBranchById(id);
    }

    @Override
    public Mono<Branch> updateBranchName(Long id, String name) {
        return branchServicePort.updateBranchName(id, name);
    }
}
