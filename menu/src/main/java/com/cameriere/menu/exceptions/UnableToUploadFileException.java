package com.cameriere.menu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnableToUploadFileException extends RuntimeException {

    public UnableToUploadFileException(String message) {
        super(message);
    }
}
