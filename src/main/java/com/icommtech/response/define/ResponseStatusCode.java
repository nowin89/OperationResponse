package com.icommtech.response.define;

import com.icommtech.response.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * API 호출시 상태값 정의
 *
 * @author: Lee Hyunseung
 * @date: 2023-10-31
 */

@RequiredArgsConstructor
public enum ResponseStatusCode implements OperationErrorCode{
    SUCCESS("SUCCESS", "SUCCESS", HttpStatus.OK),
    NO_DATA("NO_DATA", "NO_DATA", HttpStatus.NO_CONTENT),
    INSPECTION_ERROR("INSPECTION_ERROR", "INSPECTION_ERROR", HttpStatus.OK),
    ERROR("ERROR", "ERROR", HttpStatus.OK),
    WARNING("WARNING", "WARNING", HttpStatus.OK),
    NO_ACCESS("NO_ACCESS", "NO_ACCESS", HttpStatus.OK);

    private final String CODE;
    private final String MESSAGE;
    private final HttpStatus STATUS;


    @Override
    public String code() {
        return this.CODE;
    }

    @Override
    public HttpStatus defaultHttpStatus() {
        return this.STATUS;
    }

    @Override
    public String defaultMessage() {
        return this.MESSAGE;
    }

    @Override
    public RuntimeException defaultException() {
        return new OperationFailException(this);
    }

    @Override
    public RuntimeException defaultException(Throwable cause) {
        return new OperationFailException(this, cause);
    }
}
