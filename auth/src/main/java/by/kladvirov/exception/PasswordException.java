package by.kladvirov.exception;

import org.springframework.http.HttpStatus;

public class PasswordException extends RuntimeException {

    private HttpStatus httpStatus;

    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
