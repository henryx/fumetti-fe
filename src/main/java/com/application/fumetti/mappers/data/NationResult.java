package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NationResult {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("currency")
    private CurrencyResult currency;

    @JsonCreator
    public NationResult() {
    }

    public NationResult(Long id, String name, String sign, CurrencyResult currency) {
        this.id = id;
        this.name = name;
        this.sign = sign;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public NationResult setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public NationResult setName(String name) {
        this.name = name;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public NationResult setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public CurrencyResult getCurrency() {
        return currency;
    }

    public NationResult setCurrency(CurrencyResult currency) {
        this.currency = currency;
        return this;
    }
}
