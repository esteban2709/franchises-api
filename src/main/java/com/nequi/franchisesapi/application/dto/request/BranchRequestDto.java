package com.nequi.franchisesapi.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchRequestDto {
    private String name;
    private Long franchiseId;
}
