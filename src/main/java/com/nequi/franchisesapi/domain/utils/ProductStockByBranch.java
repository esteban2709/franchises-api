package com.nequi.franchisesapi.domain.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockByBranch {

    private Long branchId;
    private String branchName;
    private Long productId;
    private String productName;
    private Integer stock;
}
