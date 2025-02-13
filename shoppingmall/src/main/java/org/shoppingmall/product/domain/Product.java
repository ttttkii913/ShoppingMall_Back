package org.shoppingmall.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.category.domain.Category;
import org.shoppingmall.product.api.dto.request.ProductUpdateReqDto;
import org.shoppingmall.review.domain.Review;
import org.shoppingmall.user.domain.User;

import java.util.ArrayList;
import java.util.List;

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
    private Integer price;

    @Column(name = "product_info")
    private String info;

    @Column(name = "product_stock")
    private Integer stock;

    @Column(name = "product_image")
    private String productImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
    private ProductStatus productStatus;

    // fk
    // 하나의 카테고리에 여러 개의 상품이 있다.
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // 하나의 장바구니에는 여러 개의 상품이 등록될 수 있다.
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // 한 명의 사용자가 여러 개의 상품을 등록할 수 있다.
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 한 개의 상품에는 여러 개의 댓글이 달릴 수 있다.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Product(String name, Integer price, String info, Integer stock, ProductStatus productStatus, String productImage, User user, Category category) {
        this.name = name;
        this.price = price;
        this.info = info;
        this.stock = stock;
        this.productStatus = productStatus;
        this.productImage = productImage;
        this.user = user;
        this.category = category;
    }

    public void update(ProductUpdateReqDto productUpdateReqDto) {
        this.name = productUpdateReqDto.name();
        this.info = productUpdateReqDto.info();
        this.stock = productUpdateReqDto.stock();
        this.productStatus = productUpdateReqDto.productStatus();
    }

    public void updateImage(String productImage) {
        this.productImage = productImage;
    }
}
