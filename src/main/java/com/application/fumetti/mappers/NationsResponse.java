package com.application.fumetti.mappers;

import com.application.fumetti.mappers.data.NationResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public class NationsResponse extends Response<NationsResponse> {
    @JsonProperty("data")
    private List<NationResult> data;

    @JsonCreator
    public NationsResponse() {
    }

    public List<NationResult> getData() {
        return data;
    }

    public NationsResponse setData(List<NationResult> data) {
        this.data = data;
        return this;
    }

    public static NationsResponse map(String data) throws JsonProcessingException {
        return mapper.readValue(data, NationsResponse.class);
    }
}
