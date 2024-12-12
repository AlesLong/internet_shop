package com.yevbes.internet_shop.exception;

public class NotEnoughQuantityOfItemException extends RuntimeException {
    public NotEnoughQuantityOfItemException(String message) {
        super(message);
    }
}
