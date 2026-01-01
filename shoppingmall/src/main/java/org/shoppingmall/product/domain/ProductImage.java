package org.shoppingmall.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long id;

    @Column(name = "product_image_url")
    private String imgUrl;

    @Column(name = "product_image_order")
    private Integer imageOrder;

    @Column(name = "product_image_main")
    private Boolean isMainImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public ProductImage(Product product, String imgUrl, Integer order, Boolean isMain) {
        this.product = product;
        this.imgUrl = imgUrl;
        this.imageOrder = order;
        this.isMainImg = isMain;
    }
}
