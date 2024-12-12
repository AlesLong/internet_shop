package com.yevbes.internet_shop.exception;

public class GoodNotFoundException extends RuntimeException {
    public GoodNotFoundException(String message) {
        super(message);
    }
}
