package com.nequi.franchisesapi.infraestructure.out.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("franchises")
public class FranchiseEntity {

    @Id
    private Long id;
    private String name;

}
