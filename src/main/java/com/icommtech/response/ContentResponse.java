package com.icommtech.response;

import com.icommtech.response.define.ResponseStatusCode;

/**
 * rest controller에서 응답 데이터 정의
 * Rest Api 성공 유무와 메시지,
 * 반환할 데이터가 리턴된다.
 * @author: Lee Hyunseung
 * @date: 2023-10-31
 */
public class ContentResponse <T> extends OperationResponse{

    public ContentResponse(ResponseStatusCode operationStatus, String operationMessage) {
        super(operationStatus, operationMessage);
        // TODO Auto-generated constructor stub
    }

    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T data) {
        this.content = data;
    }

    public void setSuccessResponse(ResponseStatusCode result, String msg, T content) {
        super.setOperationStatus(result);
        super.setOperationMessage(msg);
        this.content = content;
    }
}
