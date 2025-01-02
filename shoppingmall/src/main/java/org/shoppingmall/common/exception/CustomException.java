package org.shoppingmall.common.exception;

import lombok.Getter;
import org.shoppingmall.common.error.ErrorCode;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomException(ErrorCode error, String message) {
        super(message);
        this.errorCode = error;
    }

}
