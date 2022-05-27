package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NationResult(@JsonProperty("id") Long id,
                           @JsonProperty("name") String name,
                           @JsonProperty("sign") String sign,
                           @JsonProperty("currency") CurrencyResult currency) {
}
