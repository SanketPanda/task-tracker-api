package com.task.tracker.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseServiceException extends RuntimeException{

    private static final long serialVersionUID = -8085721068591447045L;

    public abstract HttpStatus getStatus();

    public BaseServiceException(){}

    public BaseServiceException(String message){super(message);}

    public BaseServiceException(Throwable cause){super(cause);}

    public BaseServiceException(String message, Throwable cause){super(message, cause);}
}
