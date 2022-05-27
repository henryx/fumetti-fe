package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.HashMap;

public record EditorResult(@JsonProperty("id") Long id,
                           @JsonProperty("name") String name,
                           @JsonProperty("hq") String hq,
                           @JsonProperty("website") String website,
                           @JsonProperty("nation") NationResult nation) {
    public static EditorResult map(HashMap<String, Object> data) {
        var nestedMap = (HashMap<String, Object>) data.get("nation");
        var nation = NationResult.map(nestedMap);

        return new EditorResult(Long.getLong(data.get("id").toString()), data.get("name").toString(),
                data.get("hq").toString(), data.get("website").toString(), nation);
    }
}
