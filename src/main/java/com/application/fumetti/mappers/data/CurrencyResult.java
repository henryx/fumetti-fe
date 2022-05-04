package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyResult {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("symbol")
    private String symbol;

    @JsonCreator
    public CurrencyResult() {
    }

    public Integer getId() {
        return id;
    }

    public CurrencyResult setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CurrencyResult setName(String name) {
        this.name = name;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public CurrencyResult setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }
}
