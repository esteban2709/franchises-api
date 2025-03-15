package com.nequi.franchisesapi.infraestructure.configuration;

import com.nequi.franchisesapi.domain.api.IBranchServicePort;
import com.nequi.franchisesapi.domain.api.IFranchiseServicePort;
import com.nequi.franchisesapi.domain.spi.IBranchPersistencePort;
import com.nequi.franchisesapi.domain.spi.IFranchisePersistencePort;
import com.nequi.franchisesapi.domain.usecase.BranchUseCase;
import com.nequi.franchisesapi.domain.usecase.FranchiseUseCase;
import com.nequi.franchisesapi.infraestructure.out.persistence.adapter.BranchPersistenceAdapter;
import com.nequi.franchisesapi.infraestructure.out.persistence.adapter.FranchisePersistenceAdapter;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IBranchEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IFranchiseEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IBranchRepository;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IFranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IFranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper franchiseEntityMapper;

    private final IBranchRepository branchRepository;
    private final IBranchEntityMapper branchEntityMapper;

    @Bean
    public IFranchisePersistencePort franchisePersistencePort() {
        return new FranchisePersistenceAdapter(franchiseRepository, franchiseEntityMapper);
    }

    @Bean
    public IFranchiseServicePort franchiseServicePort() {
        return new FranchiseUseCase(franchisePersistencePort());
    }

    @Bean
    public IBranchPersistencePort branchPersistencePort() {
        return new BranchPersistenceAdapter(branchRepository, branchEntityMapper);
    }

    @Bean
    public IBranchServicePort branchServicePort() {
        return new BranchUseCase(branchPersistencePort());
    }
}
