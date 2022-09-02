package com.application.fumetti.frontend.mappers.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record EditorData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                         @JsonProperty("name") String name,
                         @JsonProperty("hq") String hq,
                         @JsonProperty("website") String website,
                         @JsonProperty("nation") NationData nation) {
    public static EditorData map(HashMap<String, Object> data) {
        @SuppressWarnings("unchecked") var nestedMap = (HashMap<String, Object>) data.get("nation");
        var nation = NationData.map(nestedMap);

        return new EditorData(Long.valueOf(data.get("id").toString()), data.get("name").toString(),
                data.get("hq").toString(), data.get("website").toString(), nation);
    }
}
