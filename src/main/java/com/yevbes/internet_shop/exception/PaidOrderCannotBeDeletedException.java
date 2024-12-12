package com.yevbes.internet_shop.exception;

public class PaidOrderCannotBeDeletedException extends RuntimeException {
    public PaidOrderCannotBeDeletedException(String message) {
        super(message);
    }
}
