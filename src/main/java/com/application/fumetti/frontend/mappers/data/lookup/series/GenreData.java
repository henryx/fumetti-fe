package com.application.fumetti.frontend.mappers.data.lookup.series;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record GenreData(@JsonProperty("id") Long id,
                        @JsonProperty("description") String description) {

    public static GenreData map(HashMap<String, Object> data) {
        return new GenreData(Long.valueOf(data.get("id").toString()), data.get("description").toString());
    }
}
