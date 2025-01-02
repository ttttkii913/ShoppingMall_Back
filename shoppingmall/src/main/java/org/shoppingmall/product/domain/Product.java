package org.shoppingmall.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.category.domain.Category;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_price")
    private String price;

    @Column(name = "product_info")
    private String info;

    @Column(name = "product_stock")
    private Integer stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
    private ProductStatus status;

    // fk
    // 하나의 카테고리에 여러 개의 상품이 있다.
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // 하나의 장바구니에는 여러 개의 상품이 등록될 수 있다.
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
