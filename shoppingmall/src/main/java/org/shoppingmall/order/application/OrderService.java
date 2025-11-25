package org.shoppingmall.order.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.cart.domain.repository.CartRepository;
import org.shoppingmall.cartItem.domain.CartItem;
import org.shoppingmall.common.EntityFinderException;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.CustomException;
import org.shoppingmall.order.api.dto.response.OrderResDto;
import org.shoppingmall.order.domain.Order;
import org.shoppingmall.order.domain.repository.OrderRepository;
import org.shoppingmall.orderItem.domain.OrderItem;
import org.shoppingmall.payment.domain.Payment;
import org.shoppingmall.payment.domain.Status;
import org.shoppingmall.payment.domain.repository.PaymentRepository;
import org.shoppingmall.productoption.domain.ProductOption;
import org.shoppingmall.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final CartRepository cartRepository;
    private final EntityFinderException entityFinder;
    
    // 주문 생성 - 결제 대기 상태로 결제 api 호출하면 주문 완료 상태로 변경함으로써 주문 - 결제 로직 완성
    @Transactional
    public OrderResDto createOrder(Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);

        Cart cart = cartRepository.findByUser(user).orElseThrow(
                () -> new IllegalArgumentException("장바구니를 찾을 수 없습니다."));

        // cartItem이 없다면 주문에 성공하지 못 하도록 예외처리
        if (cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("장바구니에 상품이 없습니다.");
        }

        // 최종 결제 금액 계산
        long totalPrice = 0L;
        for (CartItem cartItem : cart.getCartItems()) {
            ProductOption productOption = cartItem.getProductOption();
            if (productOption != null) {
                totalPrice += productOption.getProduct().getPrice() * cartItem.getQuantity();
                log.info("최종 결제 금액: ", totalPrice);
            }
        }

        // 결제 정보 생성 (상태는 PENDING)
        Payment payment = Payment.builder()
                .price(totalPrice)
                .status(Status.PENDING)
                .build();
        paymentRepository.save(payment);

        // 주문 생성
        Order order = Order.builder()
                .orderUid(UUID.randomUUID().toString())
                .user(user)
                .payment(payment)
                .build();

        // 장바구니 아이템을 주문 아이템으로 변환
        for (CartItem cartItem : cart.getCartItems()) {
            ProductOption productOption = cartItem.getProductOption();  // 상품 객체 가져오기
            if (productOption != null) {
                OrderItem orderItem = OrderItem.builder()
                        .totalCount(cartItem.getQuantity())
                        .totalPrice(productOption.getProduct().getPrice() * cartItem.getQuantity())  // 총 가격 계산
                        .productOption(productOption)  // 상품 설정
                        .order(order)  // 주문과 연결
                        .build();
                order.addOrderItem(orderItem);
            }
        }

        orderRepository.save(order);

        // 장바구니 비우기
        cart.getCartItems().clear();

        return OrderResDto.from(order);

    }

    // 전체 주문 목록 조회
    @Transactional(readOnly = true)
    public List<OrderResDto> getAllOrders(Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);

        List<Order> orders = orderRepository.findAllByUser(user);

        return orders.stream()
                .map(OrderResDto::from)
                .toList();
    }

    // 상세 주문 목록 조회
    @Transactional(readOnly = true)
    public OrderResDto getOrderDetail(Long orderId, Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);

        Order order = entityFinder.getOrderById(orderId);

        // 본인 주문인지 확인
        if (!order.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION_EXCEPTION
                    , ErrorCode.NO_AUTHORIZATION_EXCEPTION.getMessage());
        }

        return OrderResDto.from(order);
    }
}
