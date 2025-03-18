package com.nequi.franchisesapi.domain.utils.validations;

import com.nequi.franchisesapi.domain.exception.CustomException;
import com.nequi.franchisesapi.domain.exception.ExceptionMessage;
import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.domain.spi.IBranchPersistencePort;
import com.nequi.franchisesapi.domain.spi.IFranchisePersistencePort;
import com.nequi.franchisesapi.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class Validation {

    private final IProductPersistencePort productPersistencePort;
    private final IBranchPersistencePort branchPersistencePort;
    private final IFranchisePersistencePort franchisePersistencePort;


    public Mono<Boolean> existBranch(Long branchId) {
        return branchPersistencePort.findBranchById(branchId)
                .switchIfEmpty(Mono.error(new CustomException(ExceptionMessage.BRANCH_NOT_FOUND.getMessage())))
                .map(branch -> true)
                .defaultIfEmpty(false);
    }

    public Mono<Boolean> existProduct(Long id) {
        return productPersistencePort.findProductById(id)
                .switchIfEmpty(Mono.error(new CustomException(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage())))
                .map(product -> true)
                .defaultIfEmpty(false);
    }

    public Mono<Product> existProductByName(String name) {
        return productPersistencePort.findProductByName(name)
                .filter(product -> product.getName().equals(name));
    }

}
