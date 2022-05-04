package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NationResult {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("currency")
    private CurrencyResult currency;

}
