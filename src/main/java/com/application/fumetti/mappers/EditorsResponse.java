package com.application.fumetti.mappers;

import com.application.fumetti.mappers.data.EditorResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public class EditorsResponse extends Response<EditorsResponse> {
    @JsonProperty("data")
    private List<EditorResult> data;

    @JsonCreator
    public EditorsResponse() {
    }

    public List<EditorResult> getData() {
        return data;
    }

    public EditorsResponse setData(List<EditorResult> data) {
        this.data = data;
        return this;
    }

    public static EditorsResponse map(String data) throws JsonProcessingException {
        return mapper.readValue(data, EditorsResponse.class);
    }
}
