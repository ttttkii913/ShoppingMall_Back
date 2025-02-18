package org.shoppingmall.payment.api;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "결제 페이지 정보 가져오기", description = "결제 페이지 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류. 관리자 문의.")
    })
    @GetMapping("/payment/{orderUid}")
    public ResponseEntity<PaymentResDto> paymentPage(@PathVariable(name = "orderUid") String orderUid) {
        PaymentResDto paymentResDto = paymentService.getPaymentInfo(orderUid);
        return new ResponseEntity<>(paymentResDto, HttpStatus.OK);
    }

    @Operation(summary = "결제 결과 콜백", description = "결제 결과를 콜백 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류. 관리자 문의.")
    })
    @PostMapping("/payment")
    public ResponseEntity<IamportResponse<Payment>> validationPayment(@RequestBody PaymentCallbackReqDto reqDto) {
        IamportResponse<Payment> response = paymentService.processPayment(reqDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "결제 성공 응답", description = "결제 성공시 응답입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류. 관리자 문의.")
    })
    @GetMapping("/success-payment")
    public ResponseEntity<String> successPaymentPage() {
        return new ResponseEntity<>("Payment Success", HttpStatus.OK);
    }

    @Operation(summary = "결제 실패 응답", description = "결제 실패시 응답입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류. 관리자 문의.")
    })
    @GetMapping("/fail-payment")
    public ResponseEntity<String> failPaymentPage() {
        return new ResponseEntity<>("Payment Failed", HttpStatus.BAD_REQUEST);
    }
}
