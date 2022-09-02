package com.application.fumetti.frontend.mappers.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.HashMap;

public record CurrencyData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                           @JsonProperty("name") String name,
                           @JsonProperty("symbol") String symbol,
                           @JsonProperty("value_lire") BigDecimal valueLire,
                           @JsonProperty("value_euro") BigDecimal valueEuro) {
    public static CurrencyData map(HashMap<String, Object> nestedMap) {
        return new CurrencyData(Long.valueOf(nestedMap.get("id").toString()), nestedMap.get("name").toString(),
                nestedMap.get("symbol").toString(), new BigDecimal(nestedMap.get("value_lire").toString()),
                new BigDecimal(nestedMap.get("value_euro").toString()));
    }
}
