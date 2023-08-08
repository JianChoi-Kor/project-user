package com.project.user.utils;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ApiResponse {

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_FAIL = "fail";
    private static final String STATUS_ERROR = "error";

    private <T, E> ResponseEntity<?> get(String status, @Nullable String message, @Nullable T data, @Nullable E errors) {

        return switch (status) {
            case STATUS_SUCCESS -> new ResponseEntity<>(SucceededBody.builder()
                    .status(status)
                    .message(message)
                    .data(data)
                    .build(),
                    HttpStatus.OK);
            case STATUS_FAIL -> new ResponseEntity<>(FailedBody.builder()
                    .status(status)
                    .message(message)
                    .errors(errors)
                    .build(),
                    HttpStatus.OK);
            case STATUS_ERROR -> new ResponseEntity<>(ErroredBody.builder()
                    .status(status)
                    .message(message)
                    .build(),
                    HttpStatus.OK);
            default -> throw new RuntimeException("Api Response Error");
        };
    }

    public <T> ResponseEntity<?> success(T data) {
        return get(STATUS_SUCCESS, null, data, null);
    }

    public ResponseEntity<?> success() {
        return get(STATUS_SUCCESS, null, null, null);
    }

    public ResponseEntity<?> fail(String message) {
        return get(STATUS_FAIL, message, null, null);
    }

    public ResponseEntity<?> fail(BindingResult bindingResult) {
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors().stream()
                .map(fieldError -> new FieldError(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        return fail(null, fieldErrorList);
    }

    public <E> ResponseEntity<?> fail(String message, E errors) {
        return get(STATUS_FAIL, message, null, errors);
    }

    public ResponseEntity<?> error(String message) {
        return get(STATUS_ERROR, message, null, null);
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SucceededBody<T> {

        private String status;
        private String message;
        private T data;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FailedBody<E> {

        private String status;
        private String message;
        private E errors;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErroredBody {

        private String status;
        private String message;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldError {

        private String field;
        private String message;
    }
}
