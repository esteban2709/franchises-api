package com.nequi.franchisesapi.application.mapper;

import com.nequi.franchisesapi.application.dto.request.BranchRequestDto;
import com.nequi.franchisesapi.domain.model.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBranchRequestMapper {

    Branch toModel(BranchRequestDto branchRequestDto);
}
