package com.nequi.franchisesapi.infraestructure.out.persistence.mapper;

import com.nequi.franchisesapi.domain.model.Franchise;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.FranchiseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IFranchiseEntityMapper {

    @Mapping(target = "name", source = "name")
    FranchiseEntity toEntity(Franchise franchise);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "id", source = "id")
    Franchise toModel(FranchiseEntity franchiseEntity);
}
