package org.shoppingmall.common.exception;

import lombok.Getter;
import org.shoppingmall.common.error.ErrorCode;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String customMessage;

    public CustomException(ErrorCode errorCode, String customMessage) {
        super(customMessage); // Exception 메시지는 customMessage
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }

}
