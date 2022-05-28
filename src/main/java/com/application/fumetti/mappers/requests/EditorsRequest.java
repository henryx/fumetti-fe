package com.application.fumetti.mappers.requests;

import com.application.fumetti.mappers.Mapper;
import com.application.fumetti.mappers.data.NationData;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EditorsRequest extends Mapper {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("hq")
    private final String hq;

    @JsonProperty("website")
    private final String website;
    @JsonProperty("nation")
    private final NationData nation;

    @JsonCreator
    public EditorsRequest(String name, String hq, String website, NationData nation) {
        this.name = name;
        this.hq = hq;
        this.website = website;
        this.nation = nation;
    }

    public String getName() {
        return name;
    }

    public String getHq() {
        return hq;
    }

    public String getWebsite() {
        return website;
    }

    public NationData getNation() {
        return nation;
    }
}
