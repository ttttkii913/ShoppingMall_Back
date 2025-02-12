package org.shoppingmall.payment.api;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.payment.api.dto.request.PaymentCallbackReqDto;
import org.shoppingmall.payment.application.PaymentService;
import org.shoppingmall.payment.api.dto.response.PaymentResDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    @Value("${imp.api.client-code}")
    private String clientCode;

    private final PaymentService paymentService;

    // 결제 페이지 정보를 가져오는 API (결제 정보 응답)
    @GetMapping("/payment/{orderUid}")
    public ResponseEntity<PaymentResDto> paymentPage(@PathVariable(name = "orderUid") String orderUid) {
        PaymentResDto paymentResDto = paymentService.getPaymentInfo(orderUid);
        return new ResponseEntity<>(paymentResDto, HttpStatus.OK);
    }

    // 결제 결과 콜백 처리
    @PostMapping("/payment")
    public ResponseEntity<IamportResponse<Payment>> validationPayment(@RequestBody PaymentCallbackReqDto reqDto) {
        IamportResponse<Payment> response = paymentService.processPayment(reqDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 결제 성공 시 응답
    @GetMapping("/success-payment")
    public ResponseEntity<String> successPaymentPage() {
        return new ResponseEntity<>("Payment Success", HttpStatus.OK);
    }

    // 결제 실패 시 응답
    @GetMapping("/fail-payment")
    public ResponseEntity<String> failPaymentPage() {
        return new ResponseEntity<>("Payment Failed", HttpStatus.BAD_REQUEST);
    }
}
