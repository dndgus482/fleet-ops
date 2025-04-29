package com.bqua.fleetops.globalconfig;

import com.bqua.fleetops.common.dto.ErrorResponse;
import com.bqua.fleetops.common.exception.BusinessException;
import com.bqua.fleetops.common.exception.NotFoundException;
import com.bqua.fleetops.common.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/*
  400 = 사용자 입력 오류
  401 = 인증 토큰 없을때
  403 = 접근권한 없을때
  404 = 리소스 찾을 수 없을때
  409 = 중복이거나 의존성 존재 등 도메인 규칙에 따라 요청을 처리 할 수 없을 때
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    /**
     * Spring Validation 이 실패할 경우 {@link MethodArgumentNotValidException}을 던진다. </br>
     * 이때 사용자에게 명확한 안내를 위해 모든 에러 메시지를 합쳐 응답한다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        String errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessages));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleUnhandledException(Throwable e) {
        log.error("Server Error", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Server Error"));
    }

}