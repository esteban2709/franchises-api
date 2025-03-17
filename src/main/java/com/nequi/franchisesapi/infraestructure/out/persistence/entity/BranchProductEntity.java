package com.nequi.franchisesapi.infraestructure.out.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("branch_products")
public class BranchProductEntity {
    @Id
    private Long id;
    @Column("product_id")
    private Long productId;
    @Column("branch_id")
    private Long branchId;
    private Integer stock;
}
