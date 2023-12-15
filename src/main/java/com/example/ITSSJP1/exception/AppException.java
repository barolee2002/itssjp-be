package com.example.ITSSJP1.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final int status;
    private final String errorDes;
    public AppException(String errorDes, int status){
        this.errorDes = errorDes;
        this.status = status;
    }
    public AppException(Errors errors){
        this.errorDes = errors.getErrorDes();
        this.status = errors.getStatus();
    }

}
