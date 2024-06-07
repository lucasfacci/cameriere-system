package com.cameriere.menu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnableToDeleteFileException extends RuntimeException {

    public UnableToDeleteFileException(String message) {
        super(message);
    }
}
