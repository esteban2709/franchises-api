package com.nequi.franchisesapi.application.dto.request;

import lombok.Data;

@Data
public class ProductRequestDto {

    private String name;
    private Long branchId;
    private Integer stock;
}
