package com.nequi.franchisesapi.application.handler.interfaces;

import com.nequi.franchisesapi.application.dto.request.FranchiseRequestDto;
import com.nequi.franchisesapi.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseHandler {

    Mono<Franchise> saveFranchise(FranchiseRequestDto franchise);
    Mono<Franchise> findFranchiseById(Long id);
    Mono<Franchise> updateFranchiseName(Long id, String name);
}
