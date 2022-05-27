package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record NationResult(@JsonProperty("id") Long id,
                           @JsonProperty("name") String name,
                           @JsonProperty("sign") String sign,
                           @JsonProperty("currency") CurrencyResult currency) {
    public static NationResult map(HashMap<String, Object> data) {
        var nestedMap = (HashMap<String, Object>) data.get("currency");
        var currency = CurrencyResult.map(nestedMap);

        return new NationResult(Long.getLong(data.get("id").toString()), data.get("name").toString(),
                data.get("sign").toString(), currency);
    }
}
