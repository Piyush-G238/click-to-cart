package com.piyush.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_order_items")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_id_gen")
    @SequenceGenerator(name = "order_item_id_gen", sequenceName = "order_item_id_gen", allocationSize = 1)
    @Column(name = "order_item_id", nullable = false)
    private Long id;

    @Column(name = "order_item_product_id")
    private Long productId;

    @Column(name = "order_item_quantity")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;
}
