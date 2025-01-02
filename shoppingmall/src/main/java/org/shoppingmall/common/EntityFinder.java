package org.shoppingmall.common;

import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.NotFoundException;

import java.util.Optional;

public class EntityFinder {

    // 엔티티 not found 처리
    public static <T> T findByIdOrThrow(Optional<T> optionalEntity, ErrorCode errorCode) {
        return optionalEntity.orElseThrow(() ->
                new NotFoundException(errorCode, errorCode.getMessage()));
    }

    public static <T> T findByEmailOrThrow(Optional<T> optionalEntity, ErrorCode errorCode) {
        return optionalEntity.orElseThrow(() ->
                new NotFoundException(errorCode, errorCode.getMessage()));
    }
}