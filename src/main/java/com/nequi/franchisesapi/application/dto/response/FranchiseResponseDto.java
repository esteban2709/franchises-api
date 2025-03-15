package com.nequi.franchisesapi.application.dto.response;

import com.nequi.franchisesapi.domain.model.Branch;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FranchiseResponseDto {
    private Long id;
    private String name;
    private List<Branch> branches;

}
