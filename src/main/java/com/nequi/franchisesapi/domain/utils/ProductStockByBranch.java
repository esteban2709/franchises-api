package com.nequi.franchisesapi.domain.utils;

import lombok.Data;

@Data
public class ProductStockByBranch {

    private Long branchId;
    private String branchName;
    private Long productId;
    private String productName;
    private Integer stock;
}
