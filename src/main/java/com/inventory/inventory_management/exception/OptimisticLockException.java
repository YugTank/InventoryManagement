package com.inventory.inventory_management.exception;

public class OptimisticLockException extends RuntimeException{

    public OptimisticLockException(String message) {
        super(message);
    }
}
