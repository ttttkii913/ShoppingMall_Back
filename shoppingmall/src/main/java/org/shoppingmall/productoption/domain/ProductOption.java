package org.shoppingmall.productoption.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shoppingmall.product.domain.Product;

@Entity
@Getter
@NoArgsConstructor
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_id")
    private Long id;

    private String size;
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_option_color")
    private ProductOptionColor productOptionColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
