package com.icommtech.response;

import com.icommtech.response.define.ResponseStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * rest controller 에서 응답 데이터 정의
 * Rest Api 성공 유무와 메시지가 리턴된다.
 * @author: Lee Hyunseung
 * @date: 2023-10-31
 */



@Getter
@Setter
@AllArgsConstructor
public class OperationResponse {

    private ResponseStatusCode operationStatus;
    private String  operationMessage;

}
