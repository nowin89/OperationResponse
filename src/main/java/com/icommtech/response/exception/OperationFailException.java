package com.icommtech.response.exception;

import com.icommtech.response.define.OperationErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Default Exception 정의
 * @author: Lee Hyunseung
 * @date: 2023-10-31
 */
public class OperationFailException extends RuntimeException{
    private static final long serialVersionUID = -4326310574490320048L;

    protected OperationErrorCode OPERATION_ERROR_CODE;

    private static OperationErrorCode getDefaultErrorCode(){
        return DefaultErrorCode.DEFAULT_ERROR_CODE;
    }

    private static class DefaultErrorCode {
        private static final OperationErrorCode DEFAULT_ERROR_CODE = new OperationErrorCode() {

            @Override
            public String code() {
                return "INTERNAL_SERVER_ERROR";
            }

            @Override
            public HttpStatus defaultHttpStatus() {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

            @Override
            public String defaultMessage() {
                return "서버 에러";
            }

            @Override
            public RuntimeException defaultException() {
                return new OperationFailException("INTERNAL_SERVER_ERROR");
            }

            @Override
            public RuntimeException defaultException(Throwable cause) {
                return new OperationFailException("INTERNAL_SERVER_ERROR", cause);
            }
        };
    }

    public OperationFailException() {
        this.OPERATION_ERROR_CODE = getDefaultErrorCode();
    }

    public OperationFailException(String message) {
        super(message);
        this.OPERATION_ERROR_CODE = getDefaultErrorCode();
    }

    public OperationFailException(String message, Throwable cause) {
        super(message, cause);
        this.OPERATION_ERROR_CODE = getDefaultErrorCode();
    }

    public OperationFailException(OperationErrorCode errorCode) {
        super(errorCode.defaultMessage());
        this.OPERATION_ERROR_CODE = errorCode;
    }

    public OperationFailException(OperationErrorCode errorCode, Throwable cause) {
        super(errorCode.defaultMessage(), cause);
        this.OPERATION_ERROR_CODE = errorCode;
    }

    public OperationErrorCode getErrorCode() {
        return OPERATION_ERROR_CODE;
    }
}
