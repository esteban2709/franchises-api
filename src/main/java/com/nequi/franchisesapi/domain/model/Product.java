package com.nequi.franchisesapi.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Product {

    private Long id;
    private String name;
    private Long branchId;
    private Integer stock;
}
