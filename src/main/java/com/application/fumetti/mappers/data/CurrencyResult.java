package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.HashMap;

public record CurrencyResult(@JsonProperty("id") Long id,
                             @JsonProperty("name") String name,
                             @JsonProperty("symbol") String symbol,
                             @JsonProperty("value_lire") BigDecimal valueLire,
                             @JsonProperty("value_euro") BigDecimal valueEuro) {
    public static CurrencyResult map(HashMap<String, Object> nestedMap) {
        return new CurrencyResult(Long.getLong(nestedMap.get("id").toString()), nestedMap.get("name").toString(),
                nestedMap.get("symbol").toString(), new BigDecimal(nestedMap.get("value_lire").toString()),
                new BigDecimal(nestedMap.get("value_euro").toString()));
    }
}
