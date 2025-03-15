package com.nequi.franchisesapi.domain.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {

    private Long id;
    private String name;
    private Integer stock;
    private Branch branch;
}
