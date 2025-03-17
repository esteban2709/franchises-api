package com.nequi.franchisesapi.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchProduct {

    private Long id;
    private Long productId;
    private Long branchId;
    private Integer stock;
}
