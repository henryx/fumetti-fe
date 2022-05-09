package com.application.fumetti.mappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Response<T> extends Mapper {

    @JsonProperty("operation")
    private String operation;

    @JsonProperty("result")
    private String result;

    @JsonProperty("message")
    private String message;

    @JsonCreator
    public Response() {
    }

    public String getOperation() {
        return operation;
    }

    public T setOperation(String operation) {
        this.operation = operation;
        return (T) this;
    }

    public String getResult() {
        return result;
    }

    public T setResult(String result) {
        this.result = result;
        return (T) this;
    }

    public String getMessage() {
        return message;
    }

    public T setMessage(String message) {
        this.message = message;
        return (T) this;
    }
}