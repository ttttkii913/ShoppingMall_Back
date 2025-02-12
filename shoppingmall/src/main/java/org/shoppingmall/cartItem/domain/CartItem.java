package org.shoppingmall.cartItem.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.product.domain.Product;

@Entity
@Getter
@Table(name = "cart_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Builder
    public CartItem(Integer quantity, Product product, Cart cart) {
        this.quantity = quantity;
        this.product = product;
        this.cart = cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
