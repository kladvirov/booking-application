package by.kladvirov.exception;

import org.springframework.http.HttpStatus;

public class TokenException extends RuntimeException {

    private HttpStatus httpStatus;

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
