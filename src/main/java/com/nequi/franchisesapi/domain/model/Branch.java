package com.nequi.franchisesapi.domain.model;

import java.util.List;

public class Branch {

    private Long id;
    private String name;
    private Franchise franchise;
    private List<Product> products;
}
