package com.nequi.franchisesapi.infraestructure.configuration;

import com.nequi.franchisesapi.domain.api.IBranchServicePort;
import com.nequi.franchisesapi.domain.api.IFranchiseServicePort;
import com.nequi.franchisesapi.domain.api.IProductServicePort;
import com.nequi.franchisesapi.domain.spi.IBranchPersistencePort;
import com.nequi.franchisesapi.domain.spi.IFranchisePersistencePort;
import com.nequi.franchisesapi.domain.spi.IProductPersistencePort;
import com.nequi.franchisesapi.domain.usecase.BranchUseCase;
import com.nequi.franchisesapi.domain.usecase.FranchiseUseCase;
import com.nequi.franchisesapi.domain.usecase.ProductUseCase;
import com.nequi.franchisesapi.domain.utils.validations.Validation;
import com.nequi.franchisesapi.infraestructure.out.persistence.adapter.BranchPersistenceAdapter;
import com.nequi.franchisesapi.infraestructure.out.persistence.adapter.FranchisePersistenceAdapter;
import com.nequi.franchisesapi.infraestructure.out.persistence.adapter.ProductPersistenceAdapter;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IBranchEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IBranchProductEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IFranchiseEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.mapper.IProductEntityMapper;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IBranchProductRepository;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IBranchRepository;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IFranchiseRepository;
import com.nequi.franchisesapi.infraestructure.out.persistence.repository.IProductRepository;
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

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    private final IBranchProductRepository branchProductRepository;
    private final IBranchProductEntityMapper branchProductEntityMapper;

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

    @Bean
    public IProductPersistencePort productPersistencePort() {
        return new ProductPersistenceAdapter(productRepository, productEntityMapper,  branchProductEntityMapper, branchProductRepository);
    }

    @Bean
    public Validation validator() {
        return new Validation(productPersistencePort(), branchPersistencePort(), franchisePersistencePort());
    }

    @Bean
    public IProductServicePort productServicePort() {
        return new ProductUseCase(productPersistencePort(), validator());
    }
}
