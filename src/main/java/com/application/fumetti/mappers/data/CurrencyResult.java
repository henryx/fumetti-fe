package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CurrencyResult(@JsonProperty("id") Long id,
                             @JsonProperty("name") String name,
                             @JsonProperty("symbol") String symbol,
                             @JsonProperty("value_lire") BigDecimal valueLire,
                             @JsonProperty("value_euro") BigDecimal valueEuro) {
}
