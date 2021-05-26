package com.demo.addressbook.exception;

import java.util.List;

public class ErrorResponse {

    private String message;
    private List<String> errors;
    private String status;

    public ErrorResponse(String message, List<String> errors, String status) {
        this.message = message;
        this.errors = errors;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
