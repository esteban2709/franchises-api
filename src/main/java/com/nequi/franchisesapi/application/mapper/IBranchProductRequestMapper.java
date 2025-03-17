package com.nequi.franchisesapi.application.mapper;

import com.nequi.franchisesapi.application.dto.request.BranchProductRequestDto;
import com.nequi.franchisesapi.domain.model.BranchProduct;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IBranchProductRequestMapper {

     BranchProduct toModel(BranchProductRequestDto branchProductRequestDto);

     BranchProductRequestDto toDto(BranchProduct branchProduct);
}
