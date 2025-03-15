package com.nequi.franchisesapi.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class Branch {

    private Long id;
    private String name;
    private Long franchiseId;
    private List<Product> products;
}
