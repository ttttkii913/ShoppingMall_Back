package org.shoppingmall.common.exception;


import org.shoppingmall.common.error.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
