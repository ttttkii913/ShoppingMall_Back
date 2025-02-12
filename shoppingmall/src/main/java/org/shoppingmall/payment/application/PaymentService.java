package org.shoppingmall.payment.application;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.order.domain.Order;
import org.shoppingmall.order.domain.repository.OrderRepository;
import org.shoppingmall.payment.api.dto.request.PaymentCallbackReqDto;
import org.shoppingmall.payment.api.dto.response.PaymentResDto;
import org.shoppingmall.payment.domain.Status;
import org.shoppingmall.payment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final IamportClient iamportClient;

    public PaymentResDto getPaymentInfo(String orderUid) {
        Order order = orderRepository.findOrderAndPaymentAndUser(orderUid)
                .orElseThrow(() -> new IllegalArgumentException("주문 내역이 없습니다."));

        return PaymentResDto.from(order);
    }

    @Transactional
    public IamportResponse<Payment> processPayment(PaymentCallbackReqDto reqDto) {
        try {
            Order order = orderRepository.findOrderAndPayment(reqDto.orderUid())
                    .orElseThrow(() -> new IllegalArgumentException("주문 내역이 없습니다."));

            // 결제 단건 조회
            IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(reqDto.paymentUid());

            // 결제 완료 및 금액 검증
            validatePayment(iamportResponse, order);

            // 결제 상태 변경
            order.getPayment().updateStatus(Status.PAID, iamportResponse.getResponse().getImpUid());

            return iamportResponse;

        } catch (IamportResponseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validatePayment(IamportResponse<Payment> iamportResponse, Order order) throws IamportResponseException, IOException {
        // 결제 완료 검증
        if (!iamportResponse.getResponse().getStatus().equals("paid")) {
            orderRepository.delete(order);
            paymentRepository.delete(order.getPayment());

            throw new RuntimeException("결제가 완료되지 않았습니다.");
        }

        // 결제 금액 검증
        Long dbPrice = order.getPayment().getPrice();
        int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

        if (iamportPrice != dbPrice) {
            orderRepository.delete(order);
            paymentRepository.delete(order.getPayment());

            // 결제금액 위변조로 의심되는 결제금액을 취소
            iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));

            throw new RuntimeException("결제 금액이 일치하지 않습니다.");
        }
    }
}