package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EditorResult {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("site")
    private String site;

    @JsonProperty("nation")
    private NationResult nation;

    @JsonProperty("website")
    private String website;

    @JsonCreator
    public EditorResult() {
    }

    public int getId() {
        return id;
    }

    public EditorResult setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EditorResult setName(String name) {
        this.name = name;
        return this;
    }

    public String getSite() {
        return site;
    }

    public EditorResult setSite(String site) {
        this.site = site;
        return this;
    }

    public NationResult getNation() {
        return nation;
    }

    public EditorResult setNation(NationResult nation) {
        this.nation = nation;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public EditorResult setWebsite(String website) {
        this.website = website;
        return this;
    }
}
