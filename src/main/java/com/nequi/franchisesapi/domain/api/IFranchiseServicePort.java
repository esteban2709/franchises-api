package com.nequi.franchisesapi.domain.api;

import com.nequi.franchisesapi.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseServicePort {

    Mono<Franchise> saveFranchise(Franchise franchise);
    Mono<Franchise> findFranchiseById(Long id);
    Mono<Franchise> updateFranchiseName(Long id, String name);
}
