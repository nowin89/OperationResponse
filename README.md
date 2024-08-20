# OperationResponse Library

```
RestController 응답 또는 에러 처리 등을 공통으로 처리

spec
 - java 17

```

## dependency
### - pom.xml
``` 
- pom.xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-web</artifactId>
        <version>6.0.11</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.2</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.28</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.9</version>
    </dependency>    
```
### - gradle
```
    implementation 'org.springframework:spring-web:6.0.11'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.15.2'
	
    // Lombok
    compileOnly 'org.projectlombok:lombok:1.18.28'
    annotationProcessor 'org.projectlombok:lombok:1.18.28'

    // SLF4J
    implementation 'org.slf4j:slf4j-api:2.0.9'
```

## 구현

|     Interface      | Description                                                    |
|:------------------:|:---------------------------------------------------------------|
| OperationErrorCode | 열거형으로 작성된 다른 에러 코드의 상위 타입 역할<br/> 각각의 커스텀한 코드들을 정의할때 상속 받아 구현 |

|        Enum        | Description                  |
|:------------------:|:-----------------------------|
| ResponseStatusCode | 기본 Response 상태 값과 에러 코드등을 정의 |

|          Class           | Description                                                                                                                           | result                                                                                                                      |
|:------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------|
|    OperationResponse     | Controller에서 결과 값을 내려주는 Response                                                                                                      | {<br/> "operationStatus" : "SUCCESS", <br/> "operationMessage" : "String" <br/>}                                            |
|     ContentResponse      | Controller에서 결과와 데이터를 내려주는 Response                                                                                                   | {<br/> "operationStatus" : "SUCCESS", <br/> "operationMessage" : "String", <br/> "content" : T(Object) <br/>}               |
|  OperationFailException  | 모든 커스텀 예외들의 상위 타입의 Exception. <br/>각각의 커스텀 예외를 정의할때 상속 받지 않고 바로 RuntimeException을 상속받아도 상관 없다.<br/> 대신 ControllerAdvice에서 일괄 처리하지 못함. | {<br/>"operationStatus": "NO_DATA",<br/>"status": 200, <br/>"operationMessage": "NO_DATA",<br/>"timestamp": "2023-11-02T11:38:27.1396337"<br/>} |


### OperationErrorCode 구현 ex)
```java

public enum UserErrorCode implements OperationErrorCode{
    USERNAME_ALREADY_EXISTS("USERNAME_ALREADY_EXISTS", "이미 사용 중인 계정입니다.", HttpStatus.CONFLICT),
    SIGN_UP_FAILED_DEFAULT("SIGN_UP_FAILED_DEFAULT", "회원 가입을 다시 진행해 주십시오. 오류가 지속되는 경우 문의하시기 바랍니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND","회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    
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

```
### CustomException 구현
```java
public class UserException extends OperationFailException{
    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(OperationErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(OperationErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
```

### 일괄 에러처리 ex)
```java
@ControllerAdvice
public class ControllerExceptionAdvice {
    
    @ExceptionHandler(value = OperationFailException.class)
    protected ResponseEntity<OperationResponse> callSpFailureException(OperationFailException e) {

        OperationErrorCode errorCode = e.getErrorCode();
        OperationErrorResponse response = OperationErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, errorCode.defaultHttpStatus());
    }
}

```

### RestController 응답 처리 ex)
```java
import com.icomtech.reponse.define.ResponseStatusEnum;
import com.icomtech.reponse.OperationResponse;
import com.icomtech.reponse.ContentResponse;

/**
 *
 */
@PostMapping("/operationExample")
public ResponseEntity<OperationResponse> getOperationMethod(ExampleVo ex) 
        throws OperationFailException {
        
    OperationResponse response = new OperationResponse(ResponseStatusEnum.SUCCESS, "SUCCESS");
    examService.insert(ex);
    
    return ResponseEntity.ok(response);
    
}

/**
 * 
 */
@PostMapping("/contentOperationExample")
public ResponseEntity<OperationResponse> getContentOperationMethod(ExampleVo ex)
        throws OperationFailException {
    
    ContentResponse<ExampleVo> response = new ContentResponse<>(ResponseStatusEnum.SUCCESS, "SUCCESS");
    ExamVo result = examService.select(ex);
    response.setContent(result);
    return ResponseEntity.ok(response);
    
}
```
    

