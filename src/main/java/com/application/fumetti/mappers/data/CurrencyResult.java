package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CurrencyResult {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("value_lire")
    private BigDecimal valueLire;

    @JsonProperty("value_euro")
    private BigDecimal valueEuro;

    @JsonCreator
    public CurrencyResult() {
    }

    public CurrencyResult(Long id, String name, String symbol, BigDecimal valueLire, BigDecimal valueEuro) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.valueLire = valueLire;
        this.valueEuro = valueEuro;
    }

    public Long getId() {
        return id;
    }

    public CurrencyResult setId(Long id) {
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

    public BigDecimal getValueLire() {
        return valueLire;
    }

    public CurrencyResult setValueLire(BigDecimal valueLire) {
        this.valueLire = valueLire;
        return this;
    }

    public BigDecimal getValueEuro() {
        return valueEuro;
    }

    public CurrencyResult setValueEuro(BigDecimal valueEuro) {
        this.valueEuro = valueEuro;
        return this;
    }
}
