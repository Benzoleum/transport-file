package com.giftgo.transport_file.exceptions;

import org.springframework.web.HttpMediaTypeNotSupportedException;

public class IncorrectFileTypeException extends HttpMediaTypeNotSupportedException {

    public IncorrectFileTypeException(String message) {
        super(message);
    }
}
