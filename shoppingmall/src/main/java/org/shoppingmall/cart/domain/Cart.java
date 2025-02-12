package org.shoppingmall.cart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.shoppingmall.cartItem.domain.CartItem;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity // 해당 클래스를 엔티티로 인식하기 위한 어노테이션
@Getter //@Setter // 생성자 자동 생성 어노테이션
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 자동 생성 어노테이션
public class Cart {

    @Id // 기본키 지정 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 속성만 자동으로 1씩 증가하여 저장 pk
    @Column(name = "cart_id") // 칼럼 이름 따로 설정
    private Long id; // 기본키는 cartId가 아닌 id로 설정하는 것이 관례, 엔티티별로 다양하게 사용하면 일관성 떨어짐

    private Integer count;

    // fk 설정
    // 한 명의 사용자는 하나의 장바구니만 사용할 수 있다.
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /*// 하나의 장바구니에는 여러 개의 상품이 등록될 수 있다.
    @JsonIgnore
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();*/

    // cartItem으로 product와 연결
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();


    @Builder
    public Cart(User user) {
        this.user = user;
    }

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
    }
}
