package ru.javawebinar.topjava.common.error;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    APP_ERROR,
    DATA_NOT_FOUND,
    DATA_ERROR,
    VALIDATION_ERROR,
    UNAUTHORIZED;


}
