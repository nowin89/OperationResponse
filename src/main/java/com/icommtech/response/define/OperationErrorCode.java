package com.icommtech.response.define;

import org.springframework.http.HttpStatus;

/**
 * @author: Lee Hyunseung
 * @date: 2023-11-02
 */
public interface OperationErrorCode {
    String code();

    HttpStatus defaultHttpStatus();

    String defaultMessage();

    RuntimeException defaultException();

    RuntimeException defaultException(Throwable cause);
}
