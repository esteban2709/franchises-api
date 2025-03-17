package com.nequi.franchisesapi.infraestructure.out.persistence.mapper;

import com.nequi.franchisesapi.domain.model.Product;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IProductEntityMapper {

     ProductEntity toEntity(Product product);

     @Mapping(target = "branchId", source = "branchId")
     @Mapping(target = "stock", source = "stock")
     @Mapping(target = "id", source = "id")
     @Mapping(target = "name", source = "name")
     Product toModel(ProductEntity productEntity);
}
