package com.nequi.franchisesapi.infraestructure.out.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("branches")
@Data
@NoArgsConstructor
public class BranchEntity {

    @Id
    private Long id;
    private String name;
    @Column("franchise_id")
    private Long franchiseId;
}
