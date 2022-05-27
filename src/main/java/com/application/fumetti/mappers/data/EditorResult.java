package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EditorResult(@JsonProperty("id") Long id,
                           @JsonProperty("name") String name,
                           @JsonProperty("hq") String hq,
                           @JsonProperty("website") String website,
                           @JsonProperty("nation") NationResult nation) {
}
