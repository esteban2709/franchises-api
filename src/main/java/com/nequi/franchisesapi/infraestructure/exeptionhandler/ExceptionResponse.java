package com.nequi.franchisesapi.infraestructure.exeptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data was found for the requested operation"),
    BRANCH_ID_REQUIRED("Branch ID is required"),
    PRODUCT_ID_REQUIRED("Product ID is required"),
    STOCK_REQUIRED("Stock is required"),
    NAME_REQUIRED("Name is required"),;

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}