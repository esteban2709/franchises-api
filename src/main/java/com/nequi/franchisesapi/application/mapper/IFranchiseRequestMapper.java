package com.nequi.franchisesapi.application.mapper;

import com.nequi.franchisesapi.application.dto.request.FranchiseRequestDto;
import com.nequi.franchisesapi.domain.model.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IFranchiseRequestMapper {

    Franchise toModel(FranchiseRequestDto franchiseRequestDto);
}
