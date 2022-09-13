package com.application.fumetti.frontend.mappers.data;

import com.application.fumetti.frontend.mappers.data.lookup.series.FrequencyData;
import com.application.fumetti.frontend.mappers.data.lookup.series.GenreData;
import com.application.fumetti.frontend.mappers.data.lookup.series.StatusData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Optional;

public record SeriesData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                         @JsonProperty("name") String name,
                         @JsonProperty("genre") GenreData genre,
                         @JsonProperty("editor") EditorData editor,
                         @JsonProperty("frequency") FrequencyData frequency,
                         @JsonProperty("status") StatusData status,
                         @JsonProperty("note") String note
) {

    public static SeriesData map(HashMap<String, Object> data) {
        @SuppressWarnings("unchecked") var nestedGenre = (HashMap<String, Object>) data.get("genre");
        @SuppressWarnings("unchecked") var nestedFrequency = (HashMap<String, Object>) data.get("frequency");
        @SuppressWarnings("unchecked") var nestedStatus = (HashMap<String, Object>) data.get("status");
        @SuppressWarnings("unchecked") var nestedEditor = (HashMap<String, Object>) data.get("editor");

        var id = Long.valueOf(data.get("id").toString());
        var name = data.get("name").toString();
        var genre = GenreData.map(nestedGenre);
        var frequency = FrequencyData.map(nestedFrequency);
        var status = StatusData.map(nestedStatus);
        var editor = EditorData.map(nestedEditor);
        var note = (Optional.ofNullable(data.get("note")).orElse("")).toString();

        return new SeriesData(id, name, genre, editor, frequency, status, note);
    }
}
