package com.pokernight.exception;

/**
 * Created by cdufresne on 1/2/16.
 */
public class GameEngineException extends RuntimeException {

    public GameEngineException(String message) {
        super(message);
    }

    public GameEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameEngineException(Throwable cause) {
        super(cause);
    }

    public GameEngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
