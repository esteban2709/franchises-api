package com.nequi.franchisesapi.infraestructure.out.persistence.mapper;

import com.nequi.franchisesapi.domain.model.BranchProduct;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.BranchProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IBranchProductEntityMapper {

     @Mapping(target = "branchId", source = "branchId")
     @Mapping(target = "productId", source = "productId")
     @Mapping(target = "stock", source = "stock")
     BranchProductEntity toEntity(BranchProduct branchProduct);

     @Mapping(target = "branchId", source = "branchId")
     @Mapping(target = "productId", source = "productId")
     @Mapping(target = "stock", source = "stock")
     BranchProduct toModel(BranchProductEntity branchProductEntity);
}
