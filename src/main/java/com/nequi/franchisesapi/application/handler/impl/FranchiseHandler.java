package com.nequi.franchisesapi.application.handler.impl;

import com.nequi.franchisesapi.application.dto.request.FranchiseRequestDto;
import com.nequi.franchisesapi.application.handler.interfaces.IFranchiseHandler;
import com.nequi.franchisesapi.application.mapper.IFranchiseRequestMapper;
import com.nequi.franchisesapi.domain.api.IFranchiseServicePort;
import com.nequi.franchisesapi.domain.model.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class FranchiseHandler implements IFranchiseHandler {

    private final IFranchiseServicePort franchiseServicePort;
    private final IFranchiseRequestMapper franchiseRequestMapper;

    @Override
    public Mono<Franchise> saveFranchise(FranchiseRequestDto franchise) {
        return franchiseServicePort.saveFranchise(franchiseRequestMapper.toModel(franchise));
    }

    @Override
    public Mono<Franchise> findFranchiseById(Long id) {
        return franchiseServicePort.findFranchiseById(id);
    }

    @Override
    public Mono<Franchise> updateFranchiseName(Long id, String name) {
        return franchiseServicePort.updateFranchiseName(id, name);
    }
}
