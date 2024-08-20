package com.icommtech.response;

import com.icommtech.response.define.OperationErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author: Lee Hyunseung
 * @date: 2023-11-02
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OperationErrorResponse {

    private String operationStatus;
    private int status;
    private String operationMessage;
    private LocalDateTime timestamp;

    public static OperationErrorResponse of(OperationErrorCode errorCode) {
        return OperationErrorResponse.builder()
                .operationStatus(errorCode.code())
                .status(errorCode.defaultHttpStatus().value())
                .operationMessage(errorCode.defaultMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

}
