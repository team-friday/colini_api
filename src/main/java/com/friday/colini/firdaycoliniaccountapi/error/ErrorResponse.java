package com.friday.colini.firdaycoliniaccountapi.error;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

    private String message;
    private String errorCode;
    private int status;
    private List<FieldError> errors;

    @Builder
    public ErrorResponse(String message,
                         String errorCode,
                         int status,
                         List<FieldError> errors) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        if(errors != null){
            this.errors = new ArrayList<>();
            this.errors.addAll(errors);
        }else{
            this.errors = new ArrayList<>();
        }
    }

    @Getter
    public static class FieldError{
        private String field;
        private String value;
        private String reason;
        @Builder
        public FieldError(String field,
                          String value,
                          String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}