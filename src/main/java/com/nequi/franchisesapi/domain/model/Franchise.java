package com.nequi.franchisesapi.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Franchise {

    private Long id;
    private String name;
    private List<Branch> branches;
}
