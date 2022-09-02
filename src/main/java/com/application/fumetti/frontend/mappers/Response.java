package com.application.fumetti.frontend.mappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public class Response<T> extends Mapper {

    @JsonProperty("operation")
    private String operation;

    @JsonProperty("result")
    private String result;

    @JsonProperty("message")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> data;

    @JsonCreator
    public Response() {
    }

    public String getOperation() {
        return operation;
    }

    public Response<T> setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public String getResult() {
        return result;
    }

    public Response<T> setResult(String result) {
        this.result = result;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Response<T> setData(List<T> data) {
        this.data = data;

        return this;
    }

    public List<T> getData() {
        return data;
    }
}