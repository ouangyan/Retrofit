package com.yan.retrofit;

public class BusinessException extends Exception {
    public final static int DefaultErrorCode = 600;

    private int errorCode = DefaultErrorCode;

    public BusinessException(String message){
        this(DefaultErrorCode,message);
    }

    public BusinessException(String message,Throwable throwable){
        super(message,throwable);
    }

    public BusinessException(int errorCode,String message){
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
