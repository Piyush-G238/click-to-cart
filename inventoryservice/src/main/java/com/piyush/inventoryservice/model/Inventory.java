package com.piyush.inventoryservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@Table(name = "tbl_inventories")
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @SequenceGenerator(name = "inventory_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_seq")
    private Long id;

    @Column(name = "inventory_name", nullable = false)
    private String name;

    @Column(name = "product_id", unique = true, nullable = false)
    private Long productId;

    @Column(name = "product_quantity", nullable = false)
    private Long quantity;
}
