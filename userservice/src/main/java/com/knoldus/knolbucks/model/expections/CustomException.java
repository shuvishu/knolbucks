package com.knoldus.knolbucks.model.expections;

public class CustomException extends RuntimeException {

    public CustomException(String customMessage) {
        super(customMessage);
    }


    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
