package com.nequi.franchisesapi.infraestructure.exeptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data was found for the requested operation"),;

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}