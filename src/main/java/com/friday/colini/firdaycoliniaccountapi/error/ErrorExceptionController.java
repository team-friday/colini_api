package com.friday.colini.firdaycoliniaccountapi.error;

import com.friday.colini.firdaycoliniaccountapi.exception.AccountNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse processValidationError(MethodArgumentNotValidException e) {
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildError(ErrorCode.INPUT_VALUE_INVALID, fieldErrors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public ErrorResponse handleAccountNotFoundException(AccountNotFoundException e){
        final ErrorCode accountNotFound = ErrorCode.ACCOUNT_NOT_FOUND;
        log.error(accountNotFound.getMessage(), e.getId());
        return buildError(accountNotFound);

    }

    private ErrorResponse buildError(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .build();
    }

    private ErrorResponse buildError(ErrorCode errorCode, List<ErrorResponse.FieldError> fieldErrors) {
        return ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .errors(fieldErrors)
                .build();
    }

    private List<ErrorResponse.FieldError> getFieldErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(error ->
                        ErrorResponse.FieldError.builder()
                                .field(error.getField())
                                .value((String) error.getRejectedValue())
                                .reason(error.getDefaultMessage())
                                .build())
                .collect(Collectors.toList());
    }
}