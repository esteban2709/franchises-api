package com.nequi.franchisesapi.infraestructure.out.persistence.mapper;

import com.nequi.franchisesapi.domain.model.Branch;
import com.nequi.franchisesapi.infraestructure.out.persistence.entity.BranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IBranchEntityMapper {

    BranchEntity toEntity(Branch branch);

    Branch toModel(BranchEntity branchEntity);

}
