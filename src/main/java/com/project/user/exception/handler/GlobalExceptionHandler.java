package com.project.user.exception.handler;

import com.project.user.utils.ApiResponse;
import com.project.user.utils.ApiResponse.FieldError;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ApiResponse apiResponse;

    /**
     * IllegalStateException 예외 처리
     *
     * @param exception IllegalStateException
     * @return ResponseEntity
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleBusinessException(IllegalStateException exception) {
        return apiResponse.fail(exception.getMessage());
    }

    /**
     * MissingServletRequestParameter 예외 처리
     *
     * @param exception MissingServletRequestParameterException
     * @return ResponseEntity
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        FieldError fieldError = new FieldError(exception.getParameterName(), "요청 값을 입력해 주세요.");
        return apiResponse.fail(null, Collections.singletonList(fieldError));
    }

    /**
     * 나머지 전체 예외에 대한 처리
     *
     * @param exception Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        log.error("", exception);
        return apiResponse.error("요청에 실패했습니다.");
    }
}
