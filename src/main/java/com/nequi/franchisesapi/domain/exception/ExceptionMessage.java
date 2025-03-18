package com.nequi.franchisesapi.domain.exception;

public enum ExceptionMessage {
    INVALID_NIT_FORMAT("NIT must contain only numeric characters"),
    BRANCH_NOT_FOUND("Branch not found"),
    PRODUCT_NOT_FOUND("Product not found"),
    FRANCHISE_NOT_FOUND("Franchise not found."),;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
