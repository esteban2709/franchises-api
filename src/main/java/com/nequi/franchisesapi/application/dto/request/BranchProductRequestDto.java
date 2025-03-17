package com.nequi.franchisesapi.application.dto.request;

import lombok.Data;

@Data
public class BranchProductRequestDto {

    private Long productId;
    private Long branchId;
    private Integer stock;
}
